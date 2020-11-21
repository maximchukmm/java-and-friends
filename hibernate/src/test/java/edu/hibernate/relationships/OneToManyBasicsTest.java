package edu.hibernate.relationships;

import edu.hibernate.base.HibernateBaseTest;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OneToManyBasicsTest extends HibernateBaseTest {

    @Test
    public void whenParentWithoutChildren_ThenRetrieveParentWithEmptyCollectionOfChildren() {
        doInTransaction(session -> {
            session.persist(new Post("Post without comments"));
        });

        doInTransaction(session -> {
            Post postWithoutComments = session.createQuery(
                "select p from Post p where p.title = 'Post without comments'", Post.class)
                .getSingleResult();

            Assert.assertTrue(postWithoutComments.getPostComments().isEmpty());
        });
    }

    @Override
    protected Class<?>[] entities() {
        return new Class<?>[]{
            Post.class,
            PostComment.class
        };
    }

    @Entity(name = "Post")
    @Table(name = "post")
    @Data
    @NoArgsConstructor
    static class Post {
        @Id
        @GeneratedValue
        private Long id;

        private String title;

        @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
        private List<PostComment> postComments = new ArrayList<>();

        Post(String title) {
            this.title = title;
        }

        void addComment(PostComment postComment) {
            postComments.add(postComment);
            postComment.setPost(this);
        }

        void removeComment(PostComment postComment) {
            postComments.remove(postComment);
            postComment.setPost(null);
        }
    }

    @Entity(name = "PostComment")
    @Table(name = "post_comment")
    @Data
    @NoArgsConstructor
    static class PostComment {
        @Id
        @GeneratedValue
        private Long id;

        private String text;

        @ManyToOne
        @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_post_id"))
        private Post post;

        PostComment(String text, Post post) {
            this.text = text;
            post.addComment(this);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PostComment)) return false;

            PostComment that = (PostComment) o;
            return Objects.equals(id, that.id) &&
                Objects.equals(text, that.text) &&
                Objects.equals(post.id, that.post.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, text, post.id);
        }
    }
}
