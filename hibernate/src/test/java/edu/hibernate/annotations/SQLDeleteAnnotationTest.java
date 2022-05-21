package edu.hibernate.annotations;

import edu.hibernate.base.HibernateBaseTest;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.junit.Test;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class SQLDeleteAnnotationTest extends HibernateBaseTest {
    @Override
    protected Class<?>[] entities() {
        return new Class<?>[]{
            User.class
        };
    }

    @Test
    public void whenDeleteUser_ThenUpdateHisLoginWithCurrentTimestampPostfix() {
        String login = "hiber";

        Long id = doInTransaction(session -> {
            User user = new User(login);
            session.persist(user);
            return user.getId();
        });

        doInTransaction(session -> {
            User user = session.find(User.class, id);
            session.remove(user);
        });

        doInTransaction(session -> {
            User user = session.find(User.class, id);

            assertNotEquals(user.getLogin(), login);
            assertTrue(user.getLogin().contains(login));
        });
    }

    @Entity(name = "User")
    @Table(name = "user_table")
    @SQLDelete(sql = "UPDATE user_table SET deleted = true, login = login || '__' || current_timestamp WHERE id = ?")
    @Data
    @NoArgsConstructor
    static class User {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        private boolean deleted;

        @Basic(optional = false)
        private String login;

        User(String login) {
            this.login = login;
        }
    }
}
