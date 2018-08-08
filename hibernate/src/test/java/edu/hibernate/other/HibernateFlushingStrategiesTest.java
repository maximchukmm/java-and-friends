package edu.hibernate.other;

import edu.hibernate.base.HibernateBaseTest;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.FlushMode;
import org.junit.Test;

import javax.persistence.*;

import static org.junit.Assert.assertNull;

/**
 * Based on articles:
 * <p>
 * 1) https://vladmihalcea.com/a-beginners-guide-to-jpahibernate-flush-strategies/
 * <p>
 * 2) https://vladmihalcea.com/how-does-the-auto-flush-work-in-jpa-and-hibernate/
 */
public class HibernateFlushingStrategiesTest extends HibernateBaseTest {
    @Override
    protected Class<?>[] entities() {
        return new Class[]{
            Person.class
        };
    }

    @Test
    public void whenFlushingStrategySetToManual_ThenAfterCommittingTransactionDatabaseNotUpdated() {
        Long id = doInTransaction(session -> {
            session.setHibernateFlushMode(FlushMode.MANUAL);
            Person entity = new Person("Dev", 29);
            session.persist(entity);
            return entity.getId();
        });

        doInTransaction(session -> {
            Person entityById = session.find(Person.class, id);

            assertNull(entityById);
        });
    }

    //todo override flush strategy for query

    @Entity(name = "Person")
    @Table(name = "person")
    @Data
    @NoArgsConstructor
    static class Person {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
        private String name;
        private Integer age;

        Person(String name, Integer age) {
            this.name = name;
            this.age = age;
        }
    }
}
