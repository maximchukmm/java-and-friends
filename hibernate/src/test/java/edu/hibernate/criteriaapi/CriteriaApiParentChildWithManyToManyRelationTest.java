package edu.hibernate.criteriaapi;

import edu.hibernate.base.HibernateBaseTest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.Test;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CriteriaApiParentChildWithManyToManyRelationTest extends HibernateBaseTest {

    @Test
    public void whenJoinManyToManyRelationByFieldName_ThenGenerateCorrectSqlQueryWithTwoInnerJoins() {
        doInTransaction(session -> {
            UserGroup group1 = new UserGroup("group1");
            UserGroup group2 = new UserGroup("group2");
            UserGroup group3 = new UserGroup("group3");
            session.persist(group1);
            session.persist(group2);
            session.persist(group3);

            Person person1 = new Person("person1");
            person1.getGroups().add(group1);
            person1.getGroups().add(group2);
            person1.getGroups().add(group3);
            session.persist(person1);

            Person person2 = new Person("person2");
            person2.getGroups().add(group1);
            session.persist(person2);
        });

        doInTransaction(session -> {
            Object value = session
                .createQuery("select ug from UserGroup ug where ug.name = 'group1'", UserGroup.class)
                .getSingleResult();
            String fieldName = "groups";

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Long> query = cb.createQuery(Long.class);
            Root<Person> root = query.from(Person.class);
            Join<Object, Object> join = root.join(fieldName);

            //work
            //query.select(cb.count(root)).where(cb.literal(value).in(join));

            //work
            query.select(cb.count(root)).where(cb.equal(join, value));

            long count = session.createQuery(query).getSingleResult();

            assertEquals(2L, count);
        });
    }

    @Override
    protected Class<?>[] entities() {
        return new Class[]{
            Person.class,
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

    @MappedSuperclass
    @Data
    @EqualsAndHashCode(callSuper = true)
    static class MultiGroupBaseEntity extends BaseEntity {
        @ManyToMany
        @JoinTable(inverseJoinColumns = @JoinColumn(name = "group_id", nullable = false))
        protected List<UserGroup> groups = new ArrayList<>();
    }

    @Entity(name = "Person")
    @Table(name = "person")
    @NoArgsConstructor
    @Getter
    @Setter
    static class Person extends MultiGroupBaseEntity {
        private String name;

        Person(String name) {
            this.name = name;
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
