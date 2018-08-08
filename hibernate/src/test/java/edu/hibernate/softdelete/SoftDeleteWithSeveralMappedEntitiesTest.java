package edu.hibernate.softdelete;

import edu.hibernate.base.HibernateBaseTest;
import edu.hibernate.util.HibernateUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Loader;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.junit.Test;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static edu.hibernate.util.HibernateUtils.selectAllJpa;
import static edu.hibernate.util.HibernateUtils.selectAllNative;
import static org.junit.Assert.*;

/**
 * Test based on article
 * https://vladmihalcea.com/the-best-way-to-soft-delete-with-hibernate/
 * <br>The @SqlDelete annotation allows to override the default DELETE statement executed by Hibernate.
 * <br>The @Loader annotation allows to customize the SELECT query used to load an entity by its identifier.
 * <br>The @Where clause is used for entity queries,
 * so that Hibernate can append the deleted column filtering condition to hide deleted rows.
 * <br><b>While prior to Hibernate 5.2, it was sufficient to provide the @Where clause annotation,
 * in Hibernate 5.2, it’s important to provide a custom @Loader as well so that the direct fetching works as well.</b>
 */
public class SoftDeleteWithSeveralMappedEntitiesTest extends HibernateBaseTest {
    private final String miscTagId = "Misc";

    @Override
    protected Class<?>[] entities() {
        return new Class[]{
            Post.class,
            PostComment.class,
            PostDetails.class,
            Tag.class
        };
    }

    private void prepareTags() {
        doInTransaction(session -> {
            Tag javaTag = new Tag("Java");
            session.persist(javaTag);

            Tag jpaTag = new Tag("JPA");
            session.persist(jpaTag);

            Tag hibernateTag = new Tag("Hibernate");
            session.persist(hibernateTag);

            Tag miscTag = new Tag(miscTagId);
            session.persist(miscTag);
        });
    }

    @Test
    public void prepareTags_WhenPersistTags_ThenReturnNonEmptyListBySelectAllJpa() {
        prepareTags();

        doInTransaction(session -> {
            List<Tag> tags = selectAllJpa(session, Tag.class);
            assertEquals(4, tags.size());
        });
    }

    @Test
    public void softDelete_WhenDeleteEntity_ThenSelectJpaByIdReturnNull() {
        prepareTags();

        doInTransaction(session -> {
            Tag miscTag = session.find(Tag.class, miscTagId);
            session.remove(miscTag);
        });

        doInTransaction(session -> {
            assertNull(session.find(Tag.class, miscTagId));
        });
    }

    @Test
    public void softDelete_WhenDeleteEntity_ThenNativeSelectByIdReturnThatEntity() {
        prepareTags();

        doInTransaction(session -> {
            Tag miscTag = session.find(Tag.class, miscTagId);
            session.remove(miscTag);
        });

        doInTransaction(session -> {
            Tag miscTag = HibernateUtils.selectByIdNative(session, Tag.class, miscTagId);
            assertNotNull(miscTag);
            assertEquals(miscTagId, miscTag.getId());
        });
    }

    private void preparePostWithComments() {
        doInTransaction(session -> {
            Post post = new Post();
            post.setId(1L);
            post.setTitle("Hibernate");
            session.persist(post);

            PostComment comment1 = new PostComment();
            comment1.setId(1L);
            comment1.setReview("Great!");
            post.addComment(comment1);

            PostComment comment2 = new PostComment();
            comment2.setId(2L);
            comment2.setReview("Not bad...");
            post.addComment(comment2);
        });
    }

    @Test
    public void preparePostWithComments_WhenPersistEntityWithOneToManyAssociation_ThenPersistAllChildren() {
        preparePostWithComments();

        doInTransaction(session -> {
            Post post = session.find(Post.class, 1L);
            List<Long> postCommentIds = post.getComments()
                .stream()
                .map(PostComment::getId)
                .collect(Collectors.toList());

            assertEquals(2, postCommentIds.size());
            assertTrue(postCommentIds.contains(1L));
            assertTrue(postCommentIds.contains(2L));
        });
    }

    @Test
    public void softDelete_WhenDeleteWithOneToManyAssociation_ThenAfterDeletingMappedCollectionDoesNotContainDeletedEntity() {
        preparePostWithComments();

        doInTransaction(session -> {
            Post post = session.find(Post.class, 1L);
            PostComment postComment = post.getComments()
                .stream()
                .filter(comment -> comment.getId().equals(2L))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cannot find post comment by id"));
            post.removeComment(postComment);
        });

        doInTransaction(session -> {
            Post postWithRemovedComment = session.find(Post.class, 1L);
            assertEquals(1, postWithRemovedComment.getComments().size());
            assertEquals(1L, (long) postWithRemovedComment.getComments().get(0).getId());
        });
    }

    @Test
    public void softDelete_WhenDeleteWithOneToManyAssociation_ThenAfterDeletingSelectAllJpaDoesNotReturnDeletedEntity() {
        preparePostWithComments();

        doInTransaction(session -> {
            Post post = session.find(Post.class, 1L);
            PostComment postComment = post.getComments()
                .stream()
                .filter(comment -> comment.getId().equals(2L))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cannot find post comment by id"));
            post.removeComment(postComment);
        });

        doInTransaction(session -> {
            List<PostComment> postComments = selectAllJpa(session, PostComment.class);
            assertEquals(1, postComments.size());
            assertEquals(1L, (long) postComments.get(0).getId());
        });
    }

    @Test
    public void softDelete_WhenDeleteWithOneToManyAssociation_ThenAfterDeletingSelectAllNativeReturnsDeletedEntity() {
        preparePostWithComments();

        doInTransaction(session -> {
            Post post = session.find(Post.class, 1L);
            PostComment postComment = post.getComments()
                .stream()
                .filter(comment -> comment.getId().equals(2L))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cannot find post comment by id"));
            post.removeComment(postComment);
        });

        doInTransaction(session -> {
            List<PostComment> postComments = selectAllNative(session, PostComment.class);
            postComments.sort(Comparator.comparingLong(PostComment::getId));
            assertEquals(2, postComments.size());
            assertEquals(1L, (long) postComments.get(0).getId());
            assertEquals(2L, (long) postComments.get(1).getId());
        });
    }

    private void preparePostWithTags() {
        prepareTags();
        doInTransaction(session -> {
            Post post = new Post();
            post.setId(1L);
            post.setTitle("High-Performance Java Persistence");
            session.persist(post);

            post.addTag(session.getReference(Tag.class, "Java"));
            post.addTag(session.getReference(Tag.class, "Hibernate"));
            post.addTag(session.getReference(Tag.class, miscTagId));
        });
    }

    @Test
    public void preparePostWithTags_WhenPersistEntityWithOneToManyAssociation_ThenPersistAllChildren() {
        preparePostWithTags();

        doInTransaction(session -> {
            Post post = session.find(Post.class, 1L);
            assertEquals(3, post.getTags().size());
        });
    }

    @Test
    public void softDelete_WhenDeleteWithManyToManyAssociation_ThenAfterDeletingMappedCollectionDoesNotContainDeletedEntity() {
        preparePostWithTags();

        doInTransaction(session -> {
            Tag tag = session.find(Tag.class, miscTagId);
            session.remove(tag);
        });

        doInTransaction(session -> {
            Post post = session.find(Post.class, 1L);
            assertEquals(2, post.getTags().size());
        });
    }

    @Test
    public void softDelete_WhenDeleteWithManyToManyAssociation_ThenAfterDeletingSelectByIdNativeReturnsParentEntityWithDeletedChild() {
        preparePostWithTags();

        doInTransaction(session -> {
            Tag tag = session.find(Tag.class, miscTagId);
            session.remove(tag);
        });

        doInTransaction(session -> {
            List<Tag> tags = session.createNativeQuery(
                "SELECT * " +
                    "FROM tag " +
                    "INNER JOIN post_tag ON tag.id = post_tag.tag_id " +
                    "WHERE post_tag.post_id = 1",
                Tag.class)
                .getResultList();
            assertEquals(3, tags.size());
        });
    }

    //=============================================================================
    @MappedSuperclass
    @Data
    abstract static class BaseEntity {
        private boolean deleted;
    }

    //=============================================================================
    @Entity(name = "Tag")
    @Table(name = "tag")
    @SQLDelete(sql =
        "UPDATE tag " +
            "SET deleted = true " +
            "WHERE id = ?")
    @Loader(namedQuery = "findTagById")
    @NamedQuery(name = "findTagById", query =
        "SELECT t " +
            "FROM Tag t " +
            "WHERE " +
            "t.id = ?1 AND " +
            "t.deleted = false")
    @Where(clause = "deleted = false")
    @Data
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    static class Tag extends BaseEntity {
        @Id
        private String id;

        Tag(String id) {
            this.id = id;
        }
    }

    //=============================================================================
    @Entity(name = "Post")
    @Table(name = "post")
    @SQLDelete(sql =
        "UPDATE post " +
            "SET deleted = true " +
            "WHERE id = ?")
    @Loader(namedQuery = "findPostById")
    @NamedQuery(name = "findPostById", query =
        "SELECT p " +
            "FROM Post p " +
            "WHERE " +
            "p.id = ?1 AND " +
            "p.deleted = false")
    @Where(clause = "deleted = false")
    @Data
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    static class Post extends BaseEntity {
        @Id
        private Long id;

        private String title;

        @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            orphanRemoval = true
        )
        @Cascade(org.hibernate.annotations.CascadeType.ALL)
        private List<PostComment> comments = new ArrayList<>();

        @OneToOne(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
        )
        private PostDetails details;

        @ManyToMany
        @JoinTable(
            name = "post_tag",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
        )
        private List<Tag> tags = new ArrayList<>();

        void addComment(PostComment comment) {
            comments.add(comment);
            comment.setPost(this);
        }

        void removeComment(PostComment comment) {
            comments.remove(comment);
            comment.setPost(null);
        }

        void addDetails(PostDetails details) {
            this.details = details;
            details.setPost(this);
        }

        void removeDetails() {
            this.details.setPost(null);
            this.details = null;
        }

        void addTag(Tag tag) {
            tags.add(tag);
        }
    }

    //=============================================================================
    @Entity(name = "PostComment")
    @Table(name = "post_comment")
    @SQLDelete(sql =
        "UPDATE post_comment " +
            "SET deleted = true " +
            "WHERE id = ?")
    @Loader(namedQuery = "findPostCommentById")
    @NamedQuery(name = "findPostCommentById", query =
        "SELECT pc " +
            "FROM PostComment pc " +
            "WHERE " +
            "pc.id = ?1 AND " +
            "pc.deleted = false")
    @Where(clause = "deleted = false")
    @Data
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    static class PostComment extends BaseEntity {
        @Id
        private Long id;

        private String review;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "post_id")
        private Post post;
    }

    //=============================================================================
    @Entity(name = "PostDetails")
    @Table(name = "post_details")
    @SQLDelete(sql =
        "UPDATE post_details " +
            "SET deleted = true " +
            "WHERE id = ?")
    @Loader(namedQuery = "findPostDetailsById")
    @NamedQuery(name = "findPostDetailsById", query =
        "SELECT pd " +
            "FROM PostDetails pd " +
            "WHERE " +
            "pd.id = ?1 AND " +
            "pd.deleted = false")
    @Where(clause = "deleted = false")
    @Data
    @EqualsAndHashCode(callSuper = true)
    static class PostDetails extends BaseEntity {
        @Id
        private Long id;

        @Column(name = "created_on")
        private LocalDateTime createdOn;

        @Column(name = "created_by")
        private String createdBy;

        @OneToOne(fetch = FetchType.LAZY)
        @MapsId
        private Post post;

        PostDetails() {
            createdOn = LocalDateTime.now();
        }
    }
    //=============================================================================
}
