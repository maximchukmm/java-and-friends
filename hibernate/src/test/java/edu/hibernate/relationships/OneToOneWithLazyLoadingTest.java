package edu.hibernate.relationships;

import edu.hibernate.base.HibernateBaseTest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.Before;
import org.junit.Test;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Objects;

/**
 * Based on article:
 * https://vladmihalcea.com/the-best-way-to-map-a-onetoone-relationship-with-jpa-and-hibernate/
 * <p>
 * Useful links:
 * https://hibernate.atlassian.net/browse/HHH-10771
 */
public class OneToOneWithLazyLoadingTest extends HibernateBaseTest {

    @Test
    public void whenInOneToOneRelationshipFetchTypeIsLazy_ThenDespiteThatThereTwoSelect() {
        doInTransaction(session -> {
            User user = session.find(User.class, 1L);
            System.out.println(user.getId());
        });
    }

    @Test
    public void whenSelectChildInOneToOneRelationshipFetchTypeIsLazy_ThenExecuteOneSelect() {
        doInTransaction(session -> {
            Staff staff = session.find(Staff.class, 1L);
            System.out.println(staff.getId());
        });
    }

    @Override
    protected Class<?>[] entities() {
        return new Class[]{
            User.class,
            Staff.class
        };
    }

    @Before
    public void prepareData() {
        doInTransaction(session -> {
            User user1 = new User(1L, "user1");
            Staff staff1 = new Staff(1L, "L1", "F1");
            user1.setStaff(staff1);

            User user2 = new User(2L, "user2");
            Staff staff2 = new Staff(2L, "L2", "F2");
            user2.setStaff(staff2);

            session.persist(user1);
            session.persist(user2);
        });
    }

    @Entity(name = "User")
    @Table(name = "users")
    @Getter
    @Setter
    @NoArgsConstructor
    static class User {
        @Id
        private Long id;

        @Basic(optional = false)
        @Column(unique = true)
        private String login;

        User(Long id, String login) {
            this.id = id;
            this.login = login;
        }

        @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private Staff staff;

        void setStaff(Staff staff) {
            removeStaff();
            staff.setUser(this);
            this.staff = staff;
        }

        void removeStaff() {
            if (staff != null) {
                staff.setUser(null);
                staff = null;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof User)) return false;

            User that = (User) o;
            return Objects.equals(id, that.id) &&
                Objects.equals(login, that.login);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, login);
        }

        @Override
        public String toString() {
            return "[" +
                "User :" +
                " id=" + id +
                " login=" + login +
                "]";
        }
    }

    @Entity(name = "Staff")
    @Table(name = "staff")
    @Getter
    @Setter
    @NoArgsConstructor
    static class Staff {
        @Id
        private Long id;

        private String lastName;

        private String firstName;

        Staff(Long id, String lastName, String firstName) {
            this.id = id;
            this.lastName = lastName;
            this.firstName = firstName;
        }

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "users_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_users_id"))
        private User user;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Staff)) return false;

            Staff that = (Staff) o;
            return Objects.equals(id, that.id) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(firstName, that.firstName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, lastName, firstName);
        }

        @Override
        public String toString() {
            return "[" +
                "Staff :" +
                " id=" + id +
                " lastName=" + lastName +
                " firstName=" + firstName +
                "]";
        }
    }
}
