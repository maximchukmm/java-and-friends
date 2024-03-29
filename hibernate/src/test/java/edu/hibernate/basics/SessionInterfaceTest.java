package edu.hibernate.basics;

import edu.hibernate.base.HibernateBaseTest;
import edu.hibernate.util.HibernateUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.UnresolvableObjectException;
import org.junit.Ignore;
import org.junit.Test;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Table;
import java.math.BigInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

// TODO: fix ignored tests

public class SessionInterfaceTest extends HibernateBaseTest {
    @Override
    protected Class<?>[] entities() {
        return new Class[]{
            SimpleEntity.class
        };
    }

    @Test
    public void persist_WhenPersistNewEntityWithNullId_ThenGenerateNewIdAndPersistEntity() {
        doInTransaction(session -> {
            SimpleEntity entity = new SimpleEntity("Funny");
            assertNull(entity.getId());
            session.persist(entity);
            assertNotNull(entity.getId());
        });
    }

    @Test(expected = PersistenceException.class)
    public void persist_WhenPersistNewEntityWithNonNullId_ThenThrowPersistenceException() {
        doInTransaction(session -> {
            SimpleEntity entity = new SimpleEntity(123456L, "Bad girl");
            session.persist(entity);
        });
    }

    @Test(expected = PersistenceException.class)
    public void persist_WhenPersistNewEntityWithExistingInDatabaseId_ThenPersistenceException() {
        Long idFromDatabase = doInTransaction(session -> {
            SimpleEntity entity = new SimpleEntity("Curly");
            session.persist(entity);
            return entity.getId();
        });

        doInTransaction(session -> {
            SimpleEntity entity = new SimpleEntity(idFromDatabase, "Bad boy");
            session.persist(entity);
        });
    }

    @Test
    public void persist_WhenSelectEntityFromDatabaseThroughNativeSelect_ThenPersistenceContextContainsThatEntity() {
        Long idFromDatabase = doInTransaction(session -> {
            SimpleEntity entity = new SimpleEntity("Long Story Short");
            session.persist(entity);
            return entity.getId();
        });

        doInTransaction(session -> {
            SimpleEntity entityFromNativeSelect = HibernateUtils.selectByIdNative(session, SimpleEntity.class, idFromDatabase);

            assertTrue(session.contains(entityFromNativeSelect));
        });
    }

    @Test
    public void merge_WhenMergeNewEntityWithNullId_ThenGenerateNewIdAndPersistEntity() {
        doInTransaction(session -> {
            SimpleEntity entity = new SimpleEntity("Paul");
            SimpleEntity merged = (SimpleEntity) session.merge(entity);

            assertNull(entity.getId());
            assertNotNull(merged.getId());
        });
    }

    @Test
    public void merge_WhenMergeNewEntityWithNonNullId_ThenIgnoreThatIdGenerateNewOneAndPersistEntity() {
        doInTransaction(session -> {
            Long id = 12345L;
            SimpleEntity entity = new SimpleEntity(id, "John");
            SimpleEntity merged = (SimpleEntity) session.merge(entity);

            assertEquals(id, entity.getId());
            assertNotEquals(id, merged.getId());
        });
    }

    @Test
    public void merge_WhenMergeNewEntityWithExistingInDatabaseId_ThenUpdateExistingEntity() {
        Long idFromDatabase = doInTransaction(session -> {
            SimpleEntity entity = new SimpleEntity("George");
            session.persist(entity);
            return entity.getId();
        });

        doInTransaction(session -> {
            SimpleEntity entity = new SimpleEntity(idFromDatabase, "Ringo");
            SimpleEntity merged = (SimpleEntity) session.merge(entity);

            assertEquals(idFromDatabase, merged.getId());
            assertEquals(entity.getTitle(), merged.getTitle());
        });
    }

    @Test
    public void merge_WhenMergeDetachedEntity_ThenReturnedEntityIsManagedAndGivenDetachedEntityIsStillDetached() {
        doInTransaction(session -> {
            SimpleEntity entity = new SimpleEntity("will and organization");
            session.persist(entity);
            session.flush();

            session.detach(entity);
            SimpleEntity mergedEntity = (SimpleEntity) session.merge(entity);

            assertFalse(session.contains(entity));
            assertTrue(session.contains(mergedEntity));
        });
    }

    @Test
    public void contains_WhenCallAfterFlushingSession_ThenReturnTrue() {
        doInTransaction(session -> {
            SimpleEntity entity = new SimpleEntity("industry");
            session.persist(entity);
            session.flush();

            assertTrue(session.contains(entity));
        });
    }

    @Test
    public void contains_WhenCallAfterClearingSession_ThenReturnFalse() {
        doInTransaction(session -> {
            SimpleEntity entity = new SimpleEntity("slim");
            session.persist(entity);
            session.flush();
            session.clear();

            assertFalse(session.contains(entity));
        });
    }

    @Test
    public void contains_WhenCallWithEntityBelongingToCurrentPersistenceContext_ThenReturnTrue() {
        SimpleEntity entityFromDatabase = doInTransaction(session -> {
            SimpleEntity entity = new SimpleEntity("BrainJet");
            session.persist(entity);
            return entity;
        });

        doInTransaction(session -> {
            SimpleEntity entityInCurrentPersistenceContext = (SimpleEntity) session.merge(entityFromDatabase);
            assertFalse(session.contains(entityFromDatabase));
            assertTrue(session.contains(entityInCurrentPersistenceContext));
        });
    }

    @Test
    public void contains_WhenCallWithEntityDoesNotBelongingToCurrentPersistenceContext_ThenReturnFalse() {
        SimpleEntity entityFromDatabase = doInTransaction(session -> {
            SimpleEntity entity = new SimpleEntity("BrainJet");
            session.persist(entity);
            return entity;
        });

        doInTransaction(session -> {
            assertFalse(session.contains(entityFromDatabase));
        });
    }

    @Test
    public void contains_WhenCallWithNewEntity_ThenReturnFalse() {
        doInTransaction(session -> {
            assertFalse(session.contains(new SimpleEntity("Titleless")));
        });
    }

    @Test
    public void contains_ExperimentsWithPersistenceContext_FindById() {
        SimpleEntity entityFromDb = doInTransaction(session -> {
            SimpleEntity entity = new SimpleEntity("Persistence Context");
            session.persist(entity);

            assertTrue(session.contains(entity));
            return entity;
        });

        doInTransaction(session -> {
            assertFalse(session.contains(entityFromDb));

            SimpleEntity entity1 = session.find(SimpleEntity.class, entityFromDb.getId());

            assertTrue(session.contains(entity1));

            session.clear();

            assertFalse(session.contains(entity1));

            SimpleEntity entity2 = session.find(SimpleEntity.class, entityFromDb.getId());

            assertFalse(session.contains(entity1));
            assertTrue(session.contains(entity2));
        });
    }

    @Test
    public void contains_ExperimentsWithPersistenceContext_FindByTitle() {
        SimpleEntity entityFromDb = doInTransaction(session -> {
            SimpleEntity entity = new SimpleEntity("Persistence Context");
            session.persist(entity);

            assertTrue(session.contains(entity));
            return entity;
        });

        doInTransaction(session -> {
            assertFalse(session.contains(entityFromDb));

            SimpleEntity entity1 = session.createQuery("select s from SimpleEntity s where s.title = 'Persistence Context'", SimpleEntity.class).getSingleResult();

            assertTrue(session.contains(entity1));

            session.clear();

            assertFalse(session.contains(entity1));

            SimpleEntity entity2 = session.createQuery("select s from SimpleEntity s where s.title = 'Persistence Context'", SimpleEntity.class).getSingleResult();

            assertFalse(session.contains(entity1));
            assertTrue(session.contains(entity2));
        });
    }

    @Test(expected = UnresolvableObjectException.class)
    public void refresh_WithAllowRefreshDetachedEntitySetToTrue_WhenRefreshNewNonExistingInDatabaseEntityWithNonNullId_ThenThrowUnresolvableObjectException() {
        doInTransaction(session -> {
            session.refresh(new SimpleEntity(123L, "title"));
        });
    }

    @Test
    public void refresh_WithAllowRefreshDetachedEntitySetToTrue_WhenRefreshNewEntityWithExistingInDatabaseId_ThenGetLatestVersionOfEntityFromDatabase() {
        Long id = doInTransaction(session -> {
            SimpleEntity entity = new SimpleEntity("Autumn");
            session.persist(entity);
            return entity.getId();
        });

        doInTransaction(session -> {
            SimpleEntity entity = new SimpleEntity(id, "Winter");
            session.refresh(entity);

            assertEquals("Autumn", entity.getTitle());
        });
    }

    @Test
    public void refresh_WithAllowRefreshDetachedEntitySetToTrue_WhenRefreshEntity_ThenGetLatestVersionOfEntityFromDatabase() {
        SimpleEntity oldEntity = doInTransaction(session -> {
            SimpleEntity entity = new SimpleEntity("old title");
            session.persist(entity);
            return entity;
        });

        SimpleEntity newEntity = doInTransaction(session -> {
            SimpleEntity entity = session.find(SimpleEntity.class, oldEntity.getId());
            entity.setTitle("new title");
            session.persist(entity);
            return entity;
        });

        doInTransaction(session -> {
            session.refresh(oldEntity);

            assertEquals(oldEntity, newEntity);
        });
    }

    @Test
    public void refresh_WithAllowRefreshDetachedEntitySetToTrue_WhenTryingToRefreshDetachedEntity_ThenGetLatestVersionOfEntityFromDatabase() {
        SimpleEntity detachedEntity = doInTransaction(session -> {
            SimpleEntity entity = new SimpleEntity("Clean Code");
            session.persist(entity);
            return entity;
        });

        doInTransaction(session -> {
            session.refresh(detachedEntity);
        });
    }

    @Test
    public void When_Then_1() { //todo rename
        Long id = doInTransaction(session -> {
            SimpleEntity entity = new SimpleEntity("Old Title");
            session.persist(entity);
            return entity.getId();
        });

        doInTransaction(session -> {
            SimpleEntity entityById = session.find(SimpleEntity.class, id);
            entityById.setTitle("New Title");

            SimpleEntity entityByIdByFind = null;

            try {
                entityByIdByFind = session.find(SimpleEntity.class, id);
            } catch (Exception e) {
                e.printStackTrace();
            }

            assertEquals(entityById, entityByIdByFind);
            assertSame(entityById, entityByIdByFind);
        });
    }

    @Test
    public void When_Then_2() { //todo rename
        Long id = doInTransaction(session -> {
            SimpleEntity entity = new SimpleEntity("Old Title");
            session.persist(entity);
            return entity.getId();
        });

        doInTransaction(session -> {
            SimpleEntity entityById = session.find(SimpleEntity.class, id);
            entityById.setTitle("New Title");

            SimpleEntity entityByIdByJpql = null;

            try {
                entityByIdByJpql = session.createQuery("select s from SimpleEntity s where s.id = :id", SimpleEntity.class).setParameter("id", id).getSingleResult();
            } catch (Exception e) {
                e.printStackTrace();
            }

            assertEquals(entityById, entityByIdByJpql);
            assertSame(entityById, entityByIdByJpql);
        });
    }

    @Test
    public void When_Then_3() { //todo rename
        Long id = doInTransaction(session -> {
            SimpleEntity entity = new SimpleEntity("Old Title");
            session.persist(entity);
            return entity.getId();
        });

        doInTransaction(session -> {
            SimpleEntity entityById = session.find(SimpleEntity.class, id);
            entityById.setTitle("New Title");

            SimpleEntity entityByIdByNativeSql = null;

            try {
                entityByIdByNativeSql = session.createNativeQuery("SELECT * FROM simple_entity WHERE id = :id", SimpleEntity.class).setParameter("id", id).getSingleResult();
            } catch (Exception e) {
                e.printStackTrace();
            }

            assertEquals(entityById, entityByIdByNativeSql);
            assertSame(entityById, entityByIdByNativeSql);
        });
    }

    @Test
    @Ignore
    public void When_Then_4() { //todo rename
        Long id = doInTransaction(session -> {
            SimpleEntity entity = new SimpleEntity("Old Title");
            session.persist(entity);
            return entity.getId();
        });

        doInTransaction(session -> {
            SimpleEntity entityById = session.find(SimpleEntity.class, id);
            entityById.setTitle("New Title");

            SimpleEntity entityByIdByNativeSql = null;

            try {
                entityByIdByNativeSql = session.createNativeQuery("SELECT * FROM simple_entity WHERE id = :id", SimpleEntity.class).getSingleResult();
            } catch (Exception e) {
                e.printStackTrace();
            }

            assertNotNull(entityByIdByNativeSql);
            assertEquals(entityById.getId(), entityByIdByNativeSql.getId());
            assertNotEquals(entityById.getTitle(), entityByIdByNativeSql.getTitle());
        });
    }

    @Test
    public void When_Then_6() { //todo rename
        Long id = doInTransaction(session -> {
            SimpleEntity entity = new SimpleEntity("Old Title");
            session.persist(entity);
            return entity.getId();
        });

        doInTransaction(session -> {
            SimpleEntity entityById = session.find(SimpleEntity.class, id);
            entityById.setTitle("New Title");

            SimpleEntity entityByOldTitleByJpql = null;

            try {
                entityByOldTitleByJpql = session.createQuery("select s from SimpleEntity s where s.title = 'Old Title'", SimpleEntity.class).getSingleResult();
            } catch (Exception e) {
                e.printStackTrace();
            }

            assertNull(entityByOldTitleByJpql);
        });
    }

    @Test
    @Ignore
    public void When_Then_7() { //todo rename
        Long id = doInTransaction(session -> {
            SimpleEntity entity = new SimpleEntity("Old Title");
            session.persist(entity);
            return entity.getId();
        });

        doInTransaction(session -> {
            SimpleEntity entityById = session.find(SimpleEntity.class, id);
            entityById.setTitle("New Title");

            SimpleEntity entityByOldTitleByNativeSql = null;

            try {
                entityByOldTitleByNativeSql = session.createNativeQuery("SELECT * FROM simple_entity WHERE title = 'Old Title'", SimpleEntity.class).getSingleResult();
            } catch (Exception e) {
                e.printStackTrace();
            }

            assertNull(entityByOldTitleByNativeSql);
        });
    }

    @Test
    @Ignore
    public void When_Then_8() { //todo rename
        Long id = doInTransaction(session -> {
            SimpleEntity entity = new SimpleEntity("Old Title");
            session.persist(entity);
            return entity.getId();
        });

        doInTransaction(session -> {
            SimpleEntity entityById = session.find(SimpleEntity.class, id);
            entityById.setTitle("New Title");

            SimpleEntity entityByOldTitleByNativeSql = null;

            try {
                entityByOldTitleByNativeSql = session.createNativeQuery("SELECT * FROM simple_entity WHERE title = 'Old Title'", SimpleEntity.class).getSingleResult();
            } catch (Exception e) {
                e.printStackTrace();
            }

            assertNotNull(entityByOldTitleByNativeSql);
            assertEquals(entityById.getId(), entityByOldTitleByNativeSql.getId());
            assertNotEquals(entityById.getTitle(), entityByOldTitleByNativeSql.getTitle());
        });
    }

    @Test
    public void When_Then_9() { //todo rename
        Long id = doInTransaction(session -> {
            SimpleEntity entity = new SimpleEntity("Old Title");
            session.persist(entity);
            return entity.getId();
        });

        doInTransaction(session -> {
            SimpleEntity entityById = session.find(SimpleEntity.class, id);
            entityById.setTitle("New Title");

            SimpleEntity entityByNewTitleByJpql = null;

            try {
                entityByNewTitleByJpql = session.createQuery("select s from SimpleEntity s where s.title = 'New Title'", SimpleEntity.class).getSingleResult();
            } catch (Exception e) {
                e.printStackTrace();
            }

            assertEquals(entityById, entityByNewTitleByJpql);
            assertSame(entityById, entityByNewTitleByJpql);
        });
    }

    @Test
    @Ignore
    public void When_Then_10() { //todo rename
        Long id = doInTransaction(session -> {
            SimpleEntity entity = new SimpleEntity("Old Title");
            session.persist(entity);
            return entity.getId();
        });

        doInTransaction(session -> {
            SimpleEntity entityById = session.find(SimpleEntity.class, id);
            entityById.setTitle("New Title");

            SimpleEntity entityByNewTitleByNativeSql = null;

            try {
                entityByNewTitleByNativeSql = session.createNativeQuery("SELECT * FROM simple_entity WHERE title = 'New Title'", SimpleEntity.class).getSingleResult();
            } catch (Exception e) {
                e.printStackTrace();
            }

            assertEquals(entityById, entityByNewTitleByNativeSql);
            assertSame(entityById, entityByNewTitleByNativeSql);
        });
    }

    @Test
    public void When_Then_11() { //todo rename
        Long id = doInTransaction(session -> {
            SimpleEntity entity = new SimpleEntity("Old Title");
            session.persist(entity);
            return entity.getId();
        });

        doInTransaction(session -> {
            SimpleEntity entityById = session.find(SimpleEntity.class, id);
            entityById.setTitle("New Title");

            Object entityByNewTitleByNativeSql = null;

            try {
                entityByNewTitleByNativeSql = session.createNativeQuery("SELECT * FROM simple_entity WHERE title = 'New Title'").getSingleResult();
            } catch (Exception e) {
                e.printStackTrace();
            }

            assertNull(entityByNewTitleByNativeSql);
        });
    }

    @Test
    public void When_Then_12() {
        //todo когда одна запись уже есть в базе - в транзакции добавляется новая - в той же транзакции запрос на обе записи по фильтру
    }

    @Entity(name = "SimpleEntity")
    @Table(name = "simple_entity")
    @Data
    @NoArgsConstructor
    static class SimpleEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        private String title;

        SimpleEntity(String title) {
            this.title = title;
        }

        SimpleEntity(Long id, String title) {
            this.id = id;
            this.title = title;
        }
    }
}
