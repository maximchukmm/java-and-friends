package edu.hibernate.relationships;

import edu.hibernate.base.HibernateBaseTest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.Test;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class ManyToManyWithListAndSetTest extends HibernateBaseTest {

    @Test
    public void whenRemoveFromManyToManyWithList_ThenRemoveAllMappingsAndInsertNotRemoved() {
        Long userId = doInTransaction(session -> {
            UserGroup group1 = new UserGroup("group1");
            UserGroup group2 = new UserGroup("group2");
            UserGroup group3 = new UserGroup("group3");
            session.persist(group1);
            session.persist(group2);
            session.persist(group3);

            UserWithListOfGroups userWithListOfGroups = new UserWithListOfGroups("UserWithListOfGroups");
            userWithListOfGroups.getUserGroups().add(group1);
            userWithListOfGroups.getUserGroups().add(group2);
            userWithListOfGroups.getUserGroups().add(group3);
            session.persist(userWithListOfGroups);

            return userWithListOfGroups.getId();
        });

        doInTransaction(session -> {
            UserWithListOfGroups userWithListOfGroups = session.find(UserWithListOfGroups.class, userId);

            userWithListOfGroups.getUserGroups().remove(0);

            session.persist(userWithListOfGroups);
        });

        doInTransaction(session -> {
            UserWithListOfGroups userWithListOfGroups = session.find(UserWithListOfGroups.class, userId);

            assertEquals(2, userWithListOfGroups.getUserGroups().size());
        });
    }

    @Test
    public void whenRemoveFromManyToManyWithSet_ThenRemoveOnlyNeededEntitiesWithoutAdditionalInserts() {
        Long userId = doInTransaction(session -> {
            UserGroup group1 = new UserGroup("group1");
            UserGroup group2 = new UserGroup("group2");
            UserGroup group3 = new UserGroup("group3");
            session.persist(group1);
            session.persist(group2);
            session.persist(group3);

            UserWithSetOfGroups userWithSetOfGroups = new UserWithSetOfGroups("UserWithSetOfGroups");
            userWithSetOfGroups.getUserGroups().add(group1);
            userWithSetOfGroups.getUserGroups().add(group2);
            userWithSetOfGroups.getUserGroups().add(group3);
            session.persist(userWithSetOfGroups);

            return userWithSetOfGroups.getId();
        });

        doInTransaction(session -> {
            List<UserGroup> userGroups
                = session.createQuery("select ug from UserGroup ug", UserGroup.class).getResultList();

            UserWithSetOfGroups userWithSetOfGroups = session.find(UserWithSetOfGroups.class, userId);

            userWithSetOfGroups.getUserGroups().remove(userGroups.get(0));

            session.persist(userWithSetOfGroups);
        });

        doInTransaction(session -> {
            UserWithSetOfGroups userWithSetOfGroups = session.find(UserWithSetOfGroups.class, userId);

            assertEquals(2, userWithSetOfGroups.getUserGroups().size());
        });
    }

    @Override
    protected Class<?>[] entities() {
        return new Class[]{
            UserWithListOfGroups.class,
            UserWithSetOfGroups.class,
            UserGroup.class
        };
    }

    @MappedSuperclass
    @Data
    @NoArgsConstructor
    static class BaseEntity {
        @Id
        @GeneratedValue
        private Long id;
    }

    @Entity(name = "UserWithListOfGroups")
    @Table(name = "user_with_list_of_groups")
    @NoArgsConstructor
    @Getter
    @Setter
    static class UserWithListOfGroups extends BaseEntity {
        private String name;

        @ManyToMany
        @JoinTable(inverseJoinColumns = @JoinColumn(name = "group_id"))
        private List<UserGroup> userGroups;

        UserWithListOfGroups(String name) {
            this.name = name;
            this.userGroups = new ArrayList<>();
        }
    }


    @Entity(name = "UserWithSetOfGroups")
    @Table(name = "user_with_set_of_groups")
    @NoArgsConstructor
    @Getter
    @Setter
    static class UserWithSetOfGroups extends BaseEntity {
        private String name;

        @ManyToMany
        @JoinTable(inverseJoinColumns = @JoinColumn(name = "group_id"))
        private Set<UserGroup> userGroups;

        UserWithSetOfGroups(String name) {
            this.name = name;
            this.userGroups = new HashSet<>();
        }
    }

    @Entity(name = "UserGroup")
    @Table(name = "user_group")
    @Data
    @EqualsAndHashCode(callSuper = true)
    @NoArgsConstructor
    static class UserGroup extends BaseEntity {
        private String name;

        UserGroup(String name) {
            this.name = name;
        }
    }
}
