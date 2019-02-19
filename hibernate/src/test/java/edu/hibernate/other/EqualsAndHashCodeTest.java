package edu.hibernate.other;

import edu.hibernate.base.HibernateBaseTest;
import edu.hibernate.util.HibernateUtils;
import org.hibernate.query.NativeQuery;
import org.junit.Test;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

import static org.junit.Assert.assertEquals;

//todo разобраться с equals и hashCode
/*
 * Нужно разобраться, как jpa использует equals и hashCode.
 * Данные же юнит-тесты мало проясняют последствия той или иной реализации
 * equals и hashCode, поэтому этот класс стоит удалить после того,
 * как разберусь с этой парочкой.
 */
public class EqualsAndHashCodeTest extends HibernateBaseTest {
    @Override
    protected Class<?>[] entities() {
        return new Class[]{
            BadEqualsBadHashCode.class
        };
    }

    @Test
    public void whenMergeNewEntityWithNonExistingInDatabaseId_ThenCreateNewEntity() {
        doInTransaction(session -> {
            session.persist(new BadEqualsBadHashCode("info"));
        });

        doInTransaction(session -> {
            List<BadEqualsBadHashCode> entities = HibernateUtils.selectAllJpql(session, BadEqualsBadHashCode.class);

            assertEquals(1, entities.size());
        });
    }

    @Test
    public void whenMergeNewEntityWithExistingInDatabaseId_ThenUpdateEntityInDatabase() {
        Long idFromDB = doInTransaction(session -> {
            BadEqualsBadHashCode entity = new BadEqualsBadHashCode("info");
            session.persist(entity);
            return entity.getId();
        });

        doInTransaction(session -> {
            BadEqualsBadHashCode newEntity = new BadEqualsBadHashCode(idFromDB, "new info");
            session.merge(newEntity);
        });

        doInTransaction(session -> {
            List<BadEqualsBadHashCode> entities = HibernateUtils.selectAllJpql(session, BadEqualsBadHashCode.class);

            assertEquals(1, entities.size());
            assertEquals("new info", entities.get(0).getValue());
        });
    }

    //when in the same session created and merged entity
    @Test
    public void when0() {
        doInTransaction(session -> {
            BadEqualsBadHashCode newEntity = new BadEqualsBadHashCode("info");
            session.persist(newEntity);

            BadEqualsBadHashCode entityFromContext = session.find(BadEqualsBadHashCode.class, newEntity.getId());
            session.merge(entityFromContext);
        });
    }

    //when in the same session create, get created entity from context, change that entity and then merge it
    @Test
    public void when1() {
        doInTransaction(session -> {
            BadEqualsBadHashCode newEntity = new BadEqualsBadHashCode("info");
            session.persist(newEntity);

            BadEqualsBadHashCode entityFromContext = session.find(BadEqualsBadHashCode.class, newEntity.getId());
            entityFromContext.setValue("new info");
            session.merge(entityFromContext);
        });
    }

    //when in the same session create, get created entity from context, change it and then refresh it
    @Test
    public void when2() {
        doInTransaction(session -> {
            BadEqualsBadHashCode newEntity = new BadEqualsBadHashCode("info");
            session.persist(newEntity);
            session.flush();

            BadEqualsBadHashCode entityFromContext = session.find(BadEqualsBadHashCode.class, newEntity.getId());
            entityFromContext.setValue("new info");
            session.refresh(entityFromContext);

            assertEquals("info", entityFromContext.getValue());
        });
    }

    @Test
    public void when3() {
        BadEqualsBadHashCode persistedEntity = doInTransaction(session -> {
            BadEqualsBadHashCode entity = new BadEqualsBadHashCode("info");
            session.persist(entity);
            return entity;
        });

        doInTransaction(session -> {
            NativeQuery nativeQuery = session.createNativeQuery(
                "UPDATE bad_equals_bad_hash_code " +
                    "SET value = 'new info' " +
                    "WHERE id = 1");
            nativeQuery.executeUpdate();

            BadEqualsBadHashCode mergedEntity = (BadEqualsBadHashCode) session.merge(new BadEqualsBadHashCode(1L, "info1"));
            session.flush();
//            session.refresh(mergedEntity);
            session.refresh(persistedEntity);
            System.out.println(persistedEntity);
            System.out.println(mergedEntity);
        });

        doInTransaction(session -> {
            BadEqualsBadHashCode mergedEntity = (BadEqualsBadHashCode) session.merge(new BadEqualsBadHashCode(1L, "info2"));
            session.flush();
//            session.refresh(mergedEntity);
            session.refresh(persistedEntity);
            System.out.println(persistedEntity);
            System.out.println(mergedEntity);
        });
    }

    @Entity(name = "BadEqualsBadHashCode")
    @Table(name = "bad_equals_bad_hash_code")
    static class BadEqualsBadHashCode {
        private static int hash = 0;

        @Id
        @GeneratedValue
        private Long id;

        private String value;


        BadEqualsBadHashCode() {
        }

        BadEqualsBadHashCode(Long id, String value) {
            this.id = id;
            this.value = value;
        }

        BadEqualsBadHashCode(String value) {
            this.value = value;
        }

        Long getId() {
            return id;
        }

        void setId(Long id) {
            this.id = id;
        }

        String getValue() {
            return value;
        }

        void setValue(String value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            System.out.println("EQUALS CALLED");
            return false;
        }

        @Override
        public int hashCode() {
            System.out.println("HASH CALLED: " + hash);
            return hash++;
        }
    }
}
