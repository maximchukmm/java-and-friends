package edu.hibernate.envers.differentproperties.storedataatdelete;

import edu.hibernate.base.HibernateBaseTest;
import edu.hibernate.util.HibernateUtils;
import edu.hibernate.util.RevisionPojo;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.Audited;
import org.hibernate.envers.configuration.EnversSettings;
import org.junit.Test;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class StoreDataAtDeleteTrueTest extends HibernateBaseTest {
    @Override
    protected Class<?>[] entities() {
        return new Class<?>[]{
            Book.class
        };
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Map additionalSettings() {
        Map additionalSettings = new HashMap();
        additionalSettings.put(EnversSettings.STORE_DATA_AT_DELETE, true);
        return additionalSettings;
    }

    @Test
    public void forEntitiesAtRevision_WhenPropertyStoreDataAtDeleteIsSetToTrue_ThenReturnRemovedEntityWithNonNullFields() {
        Long id = doInTransaction(session -> {
            Book book = new Book("William Somerset Maugham", "Of Human Bondage");
            session.persist(book);
            return book.getId();
        });

        Book removedBook = doInTransaction(session -> {
            Book book = session.find(Book.class, id);
            session.remove(book);
            return book;
        });

        doInTransaction(session -> {
            List<RevisionPojo> revisionPojos = HibernateUtils.selectAllRevisions(session, Book.class);
            revisionPojos.sort(Comparator.naturalOrder());
            RevisionPojo revisionOfRemovedBook = revisionPojos.get(1);

            AuditReader auditReader = AuditReaderFactory.get(session);
            Book removedBookFromRevision = (Book) auditReader
                .createQuery()
                .forEntitiesAtRevision(
                    Book.class,
                    Book.class.getName(),
                    revisionOfRemovedBook.getRev(),
                    true)
                .getSingleResult();

            assertEquals(removedBook, removedBookFromRevision);
        });
    }

    @Entity(name = "Book")
    @Table(name = "book")
    @Audited
    @Data
    @NoArgsConstructor
    static class Book {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        private String author;

        private String title;

        Book(String author, String title) {
            this.author = author;
            this.title = title;
        }
    }
}
