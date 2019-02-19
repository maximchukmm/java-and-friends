package edu.hibernate.hello;

import edu.hibernate.base.HibernateBaseTest;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import javax.persistence.*;
import java.util.List;

import static edu.hibernate.util.HibernateUtils.selectAllJpql;
import static org.junit.Assert.assertEquals;

public class HelloTest extends HibernateBaseTest {
    @Override
    protected Class<?>[] entities() {
        return new Class<?>[]{
            Hello.class
        };
    }

    @Test
    public void createAndRead_WhenCreateOneEntity_ThenSelectAllEntitiesReturnThatSingleEntity() {
        final String greetings = "Hibernate";

        doInTransaction(session -> {
            session.persist(new Hello(greetings));
        });

        doInTransaction(session -> {
            List<Hello> all = selectAllJpql(session, Hello.class);
            assertEquals(1, all.size());
            assertEquals(greetings, all.get(0).getGreetings());
        });
    }

    @Entity(name = "Hello")
    @Table(name = "hello_table")
    @Data
    @NoArgsConstructor
    static class Hello {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        private String greetings;

        Hello(String greetings) {
            this.greetings = greetings;
        }
    }
}
