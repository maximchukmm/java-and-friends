package edu.hibernate.jpql;

import edu.hibernate.base.HibernateBaseTest;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;
import java.util.List;

public class JoinTest extends HibernateBaseTest {
    @Override
    protected Class<?>[] entities() {
        return new Class[]{
            Post.class,
            PostComments.class
        };
    }

    @Test
    public void whenWithoutJoinFetch_ThenExecuteAdditionalSelect() {
        doInTransaction(session -> {
            List<PostComments> comments = session.createQuery(
                "select pc " +
                    "from PostComments pc " +
                    "where pc.comment = :comment", PostComments.class)
                .setParameter("comment", "it depends")
                .getResultList();

            comments.forEach(System.out::println);
        });
    }

    @Test
    public void whenWithJoinFetch_ThenDoNotExecuteAdditionalSelect() {
        doInTransaction(session -> {
            List<PostComments> comments = session.createQuery(
                "select pc " +
                    "from PostComments pc " +
                    "join fetch pc.post " +
                    "where pc.comment = :comment", PostComments.class)
                .setParameter("comment", "it depends")
                .getResultList();

            comments.forEach(System.out::println);
        });
    }

    @Test
    public void whenWithJoinWithoutFetch_ThenExecuteAdditionalSelect() {
        doInTransaction(session -> {
            List<PostComments> comments = session.createQuery(
                "select pc " +
                    "from PostComments pc " +
                    "join pc.post " +
                    "where pc.comment = :comment", PostComments.class)
                .setParameter("comment", "it depends")
                .getResultList();

            comments.forEach(System.out::println);
        });
    }

    @Before
    public void prepareData() {
        doInTransaction(session -> {
            Post post1 = new Post("Testing fetch join. Is it useful?");
            session.persist(post1);
            for (int i = 0; i < 3; i++) {
                session.persist(new PostComments("yes", post1));
            }
            session.persist(new PostComments("it depends", post1));

            Post post2 = new Post("Testing fetch join. Is it awful?");
            session.persist(post2);
            for (int i = 0; i < 3; i++) {
                session.persist(new PostComments("no", post2));
            }
            session.persist(new PostComments("it depends", post2));
        });
    }

    @Entity(name = "Post")
    @Table(name = "post")
    @Data
    @NoArgsConstructor
    static class Post {
        Post(String title) {
            this.title = title;
        }

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        private String title;
    }

    @Entity(name = "PostComments")
    @Table(name = "post_comments")
    @Data
    @NoArgsConstructor
    static class PostComments {
        PostComments(String comment, Post post) {
            this.comment = comment;
            this.post = post;
        }

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        private String comment;

        @JoinColumn(name = "post_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_post_id"))
        @ManyToOne(fetch = FetchType.EAGER)
        private Post post;
    }
}
