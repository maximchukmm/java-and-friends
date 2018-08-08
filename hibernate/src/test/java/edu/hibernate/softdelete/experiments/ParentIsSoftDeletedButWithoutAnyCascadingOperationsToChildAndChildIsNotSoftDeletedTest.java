package edu.hibernate.softdelete.experiments;

import edu.hibernate.base.HibernateBaseTest;
import edu.hibernate.util.HibernateUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Loader;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.junit.Test;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ParentIsSoftDeletedButWithoutAnyCascadingOperationsToChildAndChildIsNotSoftDeletedTest extends HibernateBaseTest {
    @Override
    protected Class<?>[] entities() {
        return new Class<?>[]{
            Author.class,
            Book.class
        };
    }

    @Test
    public void whenRemoveParent_ThenChildrenAreNotRemovedAndNotReturnedByJpaSelect() {
        Long id = doInTransaction(session -> {
            Author author = new Author("Eric Freeman");
            session.persist(author);
            session.flush();

            Book book = new Book("Design Patterns");
            book.setAuthor(author);
            session.persist(book);

            return author.getId();
        });

        doInTransaction(session -> {
            Author author = session.find(Author.class, id);
            session.remove(author);
        });

        doInTransaction(session -> {
            List<Book> books = HibernateUtils.selectAllJpa(session, Book.class);

            assertTrue(books.isEmpty());
        });
    }

    @Test
    public void whenRemoveParent_ThenChildrenAreNotRemovedAndReturnedByNativeSelect() {
        Long id = doInTransaction(session -> {
            Author author = new Author("Elisabeth Freeman");
            session.persist(author);
            session.flush();

            Book book = new Book("Design Patterns");
            book.setAuthor(author);
            session.persist(book);

            return author.getId();
        });

        doInTransaction(session -> {
            Author author = session.find(Author.class, id);
            session.remove(author);
        });

        doInTransaction(session -> {
            List<Book> books = HibernateUtils.selectAllNative(session, Book.class);

            assertFalse(books.isEmpty());
        });
    }

    @Entity(name = "Author")
    @Table(name = "author")
    @SQLDelete(sql = "UPDATE author SET deleted = true WHERE id = ?")
    @NamedQuery(name = "findAuthorById", query = "select a from Author a where id = ?1 and deleted = false")
    @Loader(namedQuery = "findAuthorById")
    @Where(clause = "deleted = false")
    @Data
    @NoArgsConstructor
    static class Author {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @Basic(optional = false)
        @Column(name = "deleted")
        private boolean deleted;

        @Basic(optional = false)
        @Column(name = "full_name")
        private String fullName;

        @OneToMany(
            fetch = FetchType.LAZY,
            cascade = {},
            orphanRemoval = false,
            mappedBy = "author"
        )
        List<Book> books = new ArrayList<>();

        Author(String fullName) {
            this.fullName = fullName;
        }
    }

    @Entity(name = "Book")
    @Table(name = "book")
    @Where(clause = "false = (select a.deleted from author a where a.id = author_id)")
    @Data
    @NoArgsConstructor
    static class Book {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @Basic(optional = false)
        @Column(name = "title")
        private String title;

        @ManyToOne(
            optional = false,
            fetch = FetchType.LAZY
        )
        @JoinColumn(
            name = "author_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_author_id")
        )
        private Author author;

        Book(String title) {
            this.title = title;
        }
    }
}
