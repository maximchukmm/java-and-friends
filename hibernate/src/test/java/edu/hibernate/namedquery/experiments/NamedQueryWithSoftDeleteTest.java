package edu.hibernate.namedquery.experiments;

import edu.hibernate.base.HibernateBaseTest;
import edu.hibernate.util.HibernateUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Loader;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.junit.Test;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

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
            Comment commentByIdWithoutJoin = HibernateUtils.selectByIdNative(session, Comment.class, 1L);
            System.err.println(commentByIdWithoutJoin.post.id);
            System.err.println(commentByIdWithoutJoin.getPost().getId());
//            System.err.println(comment);

            Comment commentByIdWithJoin = session.createNativeQuery(
                    "SELECT c.* FROM comment c INNER JOIN post p ON c.post_id = p.id WHERE c.id = 1",
                    Comment.class).getSingleResult();

            System.err.println(commentByIdWithJoin.post.id);
            System.err.println(commentByIdWithJoin.getPost().getId());
//            System.err.println(comment);
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
