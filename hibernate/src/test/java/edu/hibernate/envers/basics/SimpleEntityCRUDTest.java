package edu.hibernate.envers.basics;

import edu.hibernate.base.HibernateBaseTest;
import edu.hibernate.util.HibernateUtils;
import edu.hibernate.util.RevisionPojo;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RevisionType;
import org.junit.Test;

import javax.persistence.*;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Revision types:
 * ADD - 0 - insert
 * MOD - 1 - update
 * DEL - 2 - delete
 */
public class SimpleEntityCRUDTest extends HibernateBaseTest {
    @Override
    protected Class<?>[] entities() {
        return new Class<?>[]{
            SimpleEntity.class
        };
    }

    @Test
    public void whenPersistNewAuditedEntity_ThenCreateOneRevision() {
        Long id = doInTransaction(session -> {
            SimpleEntity simpleEntity = new SimpleEntity("Emancipation reform of 1861");
            session.persist(simpleEntity);
            return simpleEntity.getId();
        });

        doInTransaction(session -> {
            AuditReader auditReader = AuditReaderFactory.get(session);
            List<Number> revisions = auditReader.getRevisions(SimpleEntity.class, id);

            assertEquals(1, revisions.size());
        });
    }

    @Test
    public void whenPersistNewAuditedEntity_ThenAbleToFindThatEntityByRevisionNumber() {
        SimpleEntity createdSimpleEntity = doInTransaction(session -> {
            SimpleEntity simpleEntity = new SimpleEntity("3 March");
            session.persist(simpleEntity);
            return simpleEntity;
        });

        doInTransaction(session -> {
            RevisionPojo revisionPojo = HibernateUtils.selectAllRevisions(session, SimpleEntity.class).get(0);

            AuditReader auditReader = AuditReaderFactory.get(session);
            SimpleEntity revisionedSimpleEntity = auditReader.find(SimpleEntity.class, revisionPojo.getId().longValue(), revisionPojo.getRev());

            assertEquals(createdSimpleEntity, revisionedSimpleEntity);
        });
    }

    @Test
    public void whenPersistNewAuditedEntity_ThenCreateOneRevisionWithRevisionTypeAdd() {
        doInTransaction(session -> {
            SimpleEntity simpleEntity = new SimpleEntity("Emancipation reform of 1861");
            session.persist(simpleEntity);
        });

        doInTransaction(session -> {
            List<RevisionPojo> revisionPojos = HibernateUtils.selectAllRevisions(session, SimpleEntity.class);

            assertEquals(1, revisionPojos.size());
            assertEquals(RevisionType.ADD, revisionPojos.get(0).getRevType());
        });
    }

    @Test
    public void whenPersistNewAuditedEntityAndUpdateIt_ThenCreateTwoRevisions() {
        Long id = doInTransaction(session -> {
            SimpleEntity simpleEntity = new SimpleEntity("Emancipation reform of 1861");
            session.persist(simpleEntity);
            return simpleEntity.getId();
        });

        doInTransaction(session -> {
            SimpleEntity simpleEntity = session.find(SimpleEntity.class, id);
            simpleEntity.setData("was one of the greatest moment in the history of Russia");
            session.persist(simpleEntity);
        });

        doInTransaction(session -> {
            List<RevisionPojo> revisionPojos = HibernateUtils.selectAllRevisions(session, SimpleEntity.class);

            assertEquals(2, revisionPojos.size());
        });
    }

    @Test
    public void whenPersistNewAuditedEntityAndUpdateIt_ThenCreateTwoRevisionsOneWithAddAndModRevisionTypes() {
        Long id = doInTransaction(session -> {
            SimpleEntity simpleEntity = new SimpleEntity("Emancipation reform of 1861");
            session.persist(simpleEntity);
            return simpleEntity.getId();
        });

        doInTransaction(session -> {
            SimpleEntity simpleEntity = session.find(SimpleEntity.class, id);
            simpleEntity.setData("was one of the greatest moment in history of Russia");
            session.persist(simpleEntity);
        });

        doInTransaction(session -> {
            List<RevisionPojo> revisionPojos = HibernateUtils.selectAllRevisions(session, SimpleEntity.class);
            revisionPojos.sort(Comparator.naturalOrder());

            assertEquals(2, revisionPojos.size());
            assertEquals(RevisionType.ADD, revisionPojos.get(0).getRevType());
            assertEquals(RevisionType.MOD, revisionPojos.get(1).getRevType());
        });
    }

    @Test
    public void whenPersistNewAuditedEntityAndRemoveIt_ThenCreateTwoRevisionsOneWithAddAndDelRevisionTypes() {
        SimpleEntity newSimpleEntity = doInTransaction(session -> {
            SimpleEntity simpleEntity = new SimpleEntity("Emancipation reform of 1861");
            session.persist(simpleEntity);
            return simpleEntity;
        });

        doInTransaction(session -> {
            session.remove(newSimpleEntity);
        });

        doInTransaction(session -> {
            List<RevisionPojo> revisionPojos = HibernateUtils.selectAllRevisions(session, SimpleEntity.class);
            revisionPojos.sort(Comparator.naturalOrder());

            assertEquals(2, revisionPojos.size());
            assertEquals(RevisionType.ADD, revisionPojos.get(0).getRevType());
            assertEquals(RevisionType.DEL, revisionPojos.get(1).getRevType());
        });
    }

    @Test
    public void whenSearchEntityByRevisionWithDelType_ThenReturnNull() {
        SimpleEntity newSimpleEntity = doInTransaction(session -> {
            SimpleEntity simpleEntity = new SimpleEntity("Emancipation reform of 1861");
            session.persist(simpleEntity);
            return simpleEntity;
        });

        doInTransaction(session -> {
            session.remove(newSimpleEntity);
        });

        doInTransaction(session -> {
            List<RevisionPojo> revisionPojos = HibernateUtils.selectAllRevisions(session, SimpleEntity.class);
            revisionPojos.sort(Comparator.naturalOrder());
            RevisionPojo revisionPojoOnDelete = revisionPojos.get(1);

            AuditReader auditReader = AuditReaderFactory.get(session);
            SimpleEntity simpleEntityFromRevision
                = auditReader.find(SimpleEntity.class, revisionPojoOnDelete.getId().longValue(), revisionPojoOnDelete.getRev());

            assertNull(simpleEntityFromRevision);
        });
    }

    @Entity(name = "SimpleEntity")
    @Table(name = "simple_entity")
    @Audited
    @Data
    @NoArgsConstructor
    static class SimpleEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        private String data;

        SimpleEntity(String data) {
            this.data = data;
        }
    }
}
