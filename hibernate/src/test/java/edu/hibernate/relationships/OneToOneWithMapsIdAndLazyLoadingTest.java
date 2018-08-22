package edu.hibernate.relationships;

import edu.hibernate.base.HibernateBaseTest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Based on article:
 * <p>
 * https://vladmihalcea.com/the-best-way-to-map-a-onetoone-relationship-with-jpa-and-hibernate/
 */
public class OneToOneWithMapsIdAndLazyLoadingTest extends HibernateBaseTest {
    private static Logger LOG = LoggerFactory.getLogger(OneToOneWithMapsIdAndLazyLoadingTest.class);

    @Override
    protected Class<?>[] entities() {
        return new Class[]{
            Post.class,
            PostDetails.class
        };
    }

    @Before
    public void prepareData() {
        doInTransaction(session -> {
            Post post = new Post("The best way to map a @OneToOne relationship with JPA and Hibernate");
            PostDetails postDetails = new PostDetails(LocalDate.parse("2016-07-26"), "Vlad Mihalcea");
            postDetails.setPost(post);
            session.persist(post);
            session.persist(postDetails);
        });
    }

    @Test
    public void whenSelectPostDetailsAndDoNotWorkWithItPost_ThenExecuteOnlyOneSelect() {
        doInTransaction(session -> {
            PostDetails postDetails = session.find(PostDetails.class, 1L);
            LOG.info("Author of post is {}", postDetails.getCreatedBy());
        });
    }

    @Test
    public void whenSelectPostDetailsAndDoSomeWorkWithPost_ThenExecuteTwoSelects() {
        doInTransaction(session -> {
            PostDetails postDetails = session.find(PostDetails.class, 1L);
            LOG.info("Post \"{}\" was written by {} in {}",
                postDetails.getPost().getTitle(),
                postDetails.getCreatedBy(),
                postDetails.getCreatedOn()
            );
        });
    }

    @Entity(name = "Post")
    @Table(name = "post")
    @Getter
    @Setter
    @NoArgsConstructor
    static class Post {
        @Id
        @GeneratedValue
        private Long id;

        private String title;

        Post(String title) {
            this.title = title;
        }
    }

    @Entity(name = "PostDetails")
    @Table(name = "post_details")
    @Getter
    @Setter
    @NoArgsConstructor
    static class PostDetails {
        @Id
        private Long id;

        private LocalDate createdOn;

        private String createdBy;

        PostDetails(LocalDate createdOn, String createdBy) {
            this.createdOn = createdOn;
            this.createdBy = createdBy;
        }

        @OneToOne(optional = false, fetch = FetchType.LAZY)
        @MapsId
        private Post post;
    }
}
