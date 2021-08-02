package edu.hibernate.namedquery.experiments;

import edu.hibernate.base.HibernateBaseTest;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Loader;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.junit.Test;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import static org.junit.Assert.assertNotNull;

public class NamedQueryWithSoftDeleteTest extends HibernateBaseTest {
    @Override
    protected Class<?>[] entities() {
        return new Class[]{Post.class, Comment.class};
    }

    @Test
    public void test() {
        doInTransaction(session -> {
            Post post = new Post();
            post.setId(1L);
            post.setTitle("Test post");
            post.setDeleted(false);

            Comment comment = new Comment();
            comment.setId(1L);
            comment.setText("Test text");
            comment.setPost(post);

            session.persist(post);
            session.persist(comment);
        });

        doInTransaction(session -> {
            Post post = session.find(Post.class, 1L);
            assertNotNull(post);

            session.remove(post);
        });

        doInTransaction(session -> {
            // todo clean test
//            Comment comment = HibernateUtils.selectByIdNative(session, Comment.class, 1L);
//            System.err.println(comment.getPost().getId());
//            System.err.println(comment);

            Comment comment = session.createNativeQuery(
                    "SELECT * FROM comment c INNER JOIN post p ON c.post_id = p.id WHERE c.id = 1",
                    Comment.class).getSingleResult();

            System.err.println(comment.post.id);
            System.err.println(comment.getPost().getId());
            System.err.println(comment);
        });
    }

    @Entity(name = "Post")
    @Table(name = "post")
    @SQLDelete(sql = "update post set deleted = true where id = ?")
    @Where(clause = "deleted = false")
    @Loader(namedQuery = "findPost")
    @NamedQueries(value = {
            @NamedQuery(
                    name = "findPost",
                    query = "select p from Post p where p.id = ?1 and p.deleted = false"
            )
    })
    @Data
    @NoArgsConstructor
    static class Post {
        @Id
        private Long id;

        @Column(name = "title", nullable = false)
        private String title;

        @Column(name = "deleted", nullable = false)
        private Boolean deleted;
    }

    @Entity(name = "Comment")
    @Table(name = "comment")
    @Data
    @NoArgsConstructor
    static class Comment {
        @Id
        private Long id;

        @Column(name = "text", nullable = false)
        private String text;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
        private Post post;
    }
}
