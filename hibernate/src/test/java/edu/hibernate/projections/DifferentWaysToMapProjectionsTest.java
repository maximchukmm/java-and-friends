package edu.hibernate.projections;

import edu.hibernate.base.HibernateBaseTest;
import edu.hibernate.util.HibernateUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Based on article
 * <p>
 * https://vladmihalcea.com/the-best-way-to-map-a-projection-query-to-a-dto-with-jpa-and-hibernate/
 */
public class DifferentWaysToMapProjectionsTest extends HibernateBaseTest {

    @Override
    protected Class<?>[] entities() {
        return new Class<?>[]{
            Post.class
        };
    }

    @Before
    public void prepareData() {
        doInTransaction(session -> {
            Post post1 = new Post();
            post1.setTitle("Different ways to map a projection query to DTO");
            post1.setCreatedOn(LocalDateTime.of(2017, 8, 29, 0, 0));
            post1.setCreatedBy("Vlad Mihalcea");
            post1.setUpdatedOn(LocalDateTime.of(2018, 6, 6, 0, 0));
            post1.setUpdatedBy("Vlad Mihalcea");
            post1.setVersion(3);
            session.persist(post1);

            Post post2 = new Post();
            post2.setTitle("What Every Computer Scientist Should Know About Floating-Point Arithmetic");
            post2.setCreatedOn(LocalDateTime.of(1991, 3, 1, 0, 0));
            post2.setCreatedBy("David Goldberg");
            post2.setUpdatedOn(null);
            post2.setUpdatedBy(null);
            post2.setVersion(1);
            session.persist(post2);
        });
    }

    @Test
    public void projectionsUsingTupleAndJPQL() {
        doInTransaction(session -> {
            List<Tuple> postDTOs = session
                .createQuery(
                    "select " +
                        "p.id as id, " +
                        "p.title as title " +
                        "from Post p " +
                        "order by p.id", Tuple.class)
                .getResultList();

            List<Post> postEntities = HibernateUtils.selectAllJpa(session, Post.class);
            postEntities.sort(Comparator.comparing(Post::getId));

            for (int i = 0; i < postDTOs.size(); i++) {
                Tuple postDTO = postDTOs.get(i);
                Post postEntity = postEntities.get(i);

                assertEquals(postEntity.getId(), postDTO.get("id"));
                assertEquals(postEntity.getTitle(), postDTO.get("title"));
            }
        });
    }

    @Test
    public void projectionsUsingConstructorExpressionAndJPQL() {
        doInTransaction(session -> {
            List<PostDTO> actualPostDTOs = session
                .createQuery(
                    "select new edu.hibernate.projections.PostDTO(p.id, p.title) " +
                        "from Post p ", PostDTO.class
                )
                .getResultList();

            List<PostDTO> expectedPostDTOs = new ArrayList<>();
            expectedPostDTOs.add(new PostDTO(1L, "Different ways to map a projection query to DTO"));
            expectedPostDTOs.add(new PostDTO(2L, "What Every Computer Scientist Should Know About Floating-Point Arithmetic"));

            assertEquals(expectedPostDTOs.size(), actualPostDTOs.size());
            assertTrue(expectedPostDTOs.containsAll(actualPostDTOs));
        });
    }

    @Test
    public void projectionsUsingTupleAndNativeSQLQueries() {
        doInTransaction(session -> {
            List<Tuple> postDTOs = session
                .createNativeQuery(
                    "SELECT " +
                        "p.id AS id, " +
                        "p.title AS title " +
                        "FROM post AS p " +
                        "ORDER BY p.id", Tuple.class)
                .getResultList();

            List<Post> postEntities = HibernateUtils.selectAllJpa(session, Post.class);
            postEntities.sort(Comparator.comparing(Post::getId));

            for (int i = 0; i < postDTOs.size(); i++) {
                Tuple postDTO = postDTOs.get(i);
                Post postEntity = postEntities.get(i);

                assertEquals(
                    postEntity.getId().longValue(),
                    ((Number) postDTO.get("id")).longValue()
                );
                assertEquals(postEntity.getTitle(), postDTO.get("title"));
            }
        });
    }

    @Test
    public void projectionsUsingAConstructorResult() {
        doInTransaction(session -> {
            List<PostDTO> actualPostDTOs = (List<PostDTO>) session.createNamedQuery("PostDTO").getResultList();
            List<PostDTO> expectedPostDTOs = new ArrayList<>();
            expectedPostDTOs.add(new PostDTO(1L, "Different ways to map a projection query to DTO"));
            expectedPostDTOs.add(new PostDTO(2L, "What Every Computer Scientist Should Know About Floating-Point Arithmetic"));

            assertEquals(expectedPostDTOs.size(), actualPostDTOs.size());
            assertTrue(expectedPostDTOs.containsAll(actualPostDTOs));
        });
    }

    @Test
    public void projectionsUsingResultTransformerAndJPQL() {
        doInTransaction(session -> {
            List<PostDTO> actualPostDTOs = (List<PostDTO>) session
                .createQuery(
                    "select " +
                        "p.id as id, " +
                        "p.title as title " +
                        "from Post p " +
                        "order by p.id"
                )
                .unwrap(Query.class)
                .setResultTransformer(Transformers.aliasToBean(PostDTO.class))
                .getResultList();

            List<PostDTO> expectedPostDTOs = new ArrayList<>();
            expectedPostDTOs.add(new PostDTO(1L, "Different ways to map a projection query to DTO"));
            expectedPostDTOs.add(new PostDTO(2L, "What Every Computer Scientist Should Know About Floating-Point Arithmetic"));

            assertEquals(expectedPostDTOs.size(), actualPostDTOs.size());
            assertTrue(expectedPostDTOs.containsAll(actualPostDTOs));
        });
    }

    @Test
    public void projectionsUsingResultTransformerAndNativeSQLQueries() {
        doInTransaction(session -> {
            List<PostDTO> actualPostDTOs = (List<PostDTO>) session
                .createNativeQuery(
                    "SELECT " +
                        "p.id AS \"id\", " +
                        "p.title AS \"title\" " +
                        "FROM post p " +
                        "ORDER BY p.id")
                .unwrap(NativeQuery.class)
                .setResultTransformer(Transformers.aliasToBean(PostDTO.class))
                .getResultList();

            List<PostDTO> expectedPostDTOs = new ArrayList<>();
            expectedPostDTOs.add(new PostDTO(1L, "Different ways to map a projection query to DTO"));
            expectedPostDTOs.add(new PostDTO(2L, "What Every Computer Scientist Should Know About Floating-Point Arithmetic"));

            assertEquals(expectedPostDTOs.size(), actualPostDTOs.size());
            assertTrue(expectedPostDTOs.containsAll(actualPostDTOs));
        });
    }

    @Entity(name = "Post")
    @Table(name = "post")
    @NamedNativeQuery(
        name = "PostDTO",
        query =
            "SELECT " +
                "p.id AS id, " +
                "p.title AS title " +
                "FROM post p",
        resultSetMapping = "PostDTO"
    )
    @SqlResultSetMapping(
        name = "PostDTO",
        classes = @ConstructorResult(
            targetClass = PostDTO.class,
            columns = {
                @ColumnResult(name = "id", type = Long.class),
                @ColumnResult(name = "title", type = String.class)
            }
        )
    )
    @Data
    @NoArgsConstructor
    static class Post {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
        private String title;
        private LocalDateTime createdOn;
        private String createdBy;
        private LocalDateTime updatedOn;
        private String updatedBy;
        private Integer version;
    }
}
