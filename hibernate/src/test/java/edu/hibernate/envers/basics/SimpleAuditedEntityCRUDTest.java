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
public class SimpleAuditedEntityCRUDTest extends HibernateBaseTest {
    @Override
    protected Class<?>[] entities() {
        return new Class<?>[]{
            SimpleAuditedEntity.class
        };
    }

    @Test
    public void whenPersistNewAuditedEntity_ThenCreateOneRevision() {
        Long id = doInTransaction(session -> {
            SimpleAuditedEntity simpleAuditedEntity = new SimpleAuditedEntity("Emancipation reform of 1861");
            session.persist(simpleAuditedEntity);
            return simpleAuditedEntity.getId();
        });

        doInTransaction(session -> {
            AuditReader auditReader = AuditReaderFactory.get(session);
            List<Number> revisions = auditReader.getRevisions(SimpleAuditedEntity.class, id);

            assertEquals(1, revisions.size());
        });
    }

    @Test
    public void whenPersistNewAuditedEntity_ThenAbleToFindThatEntityByRevisionNumber() {
        SimpleAuditedEntity createdSimpleAuditedEntity = doInTransaction(session -> {
            SimpleAuditedEntity simpleAuditedEntity = new SimpleAuditedEntity("3 March");
            session.persist(simpleAuditedEntity);
            return simpleAuditedEntity;
        });

        doInTransaction(session -> {
            RevisionPojo revisionPojo = HibernateUtils.selectAllRevisions(session, SimpleAuditedEntity.class).get(0);

            AuditReader auditReader = AuditReaderFactory.get(session);
            SimpleAuditedEntity revisionedSimpleAuditedEntity = auditReader.find(SimpleAuditedEntity.class, revisionPojo.getId().longValue(), revisionPojo.getRev());

            assertEquals(createdSimpleAuditedEntity, revisionedSimpleAuditedEntity);
        });
    }

    @Test
    public void whenPersistNewAuditedEntity_ThenCreateOneRevisionWithRevisionTypeAdd() {
        doInTransaction(session -> {
            SimpleAuditedEntity simpleAuditedEntity = new SimpleAuditedEntity("Emancipation reform of 1861");
            session.persist(simpleAuditedEntity);
        });

        doInTransaction(session -> {
            List<RevisionPojo> revisionPojos = HibernateUtils.selectAllRevisions(session, SimpleAuditedEntity.class);

            assertEquals(1, revisionPojos.size());
            assertEquals(RevisionType.ADD, revisionPojos.get(0).getRevType());
        });
    }

    @Test
    public void whenPersistNewAuditedEntityAndUpdateIt_ThenCreateTwoRevisions() {
        Long id = doInTransaction(session -> {
            SimpleAuditedEntity simpleAuditedEntity = new SimpleAuditedEntity("Emancipation reform of 1861");
            session.persist(simpleAuditedEntity);
            return simpleAuditedEntity.getId();
        });

        doInTransaction(session -> {
            SimpleAuditedEntity simpleAuditedEntity = session.find(SimpleAuditedEntity.class, id);
            simpleAuditedEntity.setData("was one of the greatest moment in the history of Russia");
            session.persist(simpleAuditedEntity);
        });

        doInTransaction(session -> {
            List<RevisionPojo> revisionPojos = HibernateUtils.selectAllRevisions(session, SimpleAuditedEntity.class);

            assertEquals(2, revisionPojos.size());
        });
    }

    @Test
    public void whenPersistNewAuditedEntityAndUpdateIt_ThenCreateTwoRevisionsOneWithAddAndModRevisionTypes() {
        Long id = doInTransaction(session -> {
            SimpleAuditedEntity simpleAuditedEntity = new SimpleAuditedEntity("Emancipation reform of 1861");
            session.persist(simpleAuditedEntity);
            return simpleAuditedEntity.getId();
        });

        doInTransaction(session -> {
            SimpleAuditedEntity simpleAuditedEntity = session.find(SimpleAuditedEntity.class, id);
            simpleAuditedEntity.setData("was one of the greatest moment in history of Russia");
            session.persist(simpleAuditedEntity);
        });

        doInTransaction(session -> {
            List<RevisionPojo> revisionPojos = HibernateUtils.selectAllRevisions(session, SimpleAuditedEntity.class);
            revisionPojos.sort(Comparator.naturalOrder());

            assertEquals(2, revisionPojos.size());
            assertEquals(RevisionType.ADD, revisionPojos.get(0).getRevType());
            assertEquals(RevisionType.MOD, revisionPojos.get(1).getRevType());
        });
    }

    @Test
    public void whenPersistNewAuditedEntityAndRemoveIt_ThenCreateTwoRevisionsOneWithAddAndDelRevisionTypes() {
        SimpleAuditedEntity newSimpleAuditedEntity = doInTransaction(session -> {
            SimpleAuditedEntity simpleAuditedEntity = new SimpleAuditedEntity("Emancipation reform of 1861");
            session.persist(simpleAuditedEntity);
            return simpleAuditedEntity;
        });

        doInTransaction(session -> {
            session.remove(newSimpleAuditedEntity);
        });

        doInTransaction(session -> {
            List<RevisionPojo> revisionPojos = HibernateUtils.selectAllRevisions(session, SimpleAuditedEntity.class);
            revisionPojos.sort(Comparator.naturalOrder());

            assertEquals(2, revisionPojos.size());
            assertEquals(RevisionType.ADD, revisionPojos.get(0).getRevType());
            assertEquals(RevisionType.DEL, revisionPojos.get(1).getRevType());
        });
    }

    @Test
    public void whenSearchEntityByRevisionWithDelType_ThenReturnNull() {
        SimpleAuditedEntity newSimpleAuditedEntity = doInTransaction(session -> {
            SimpleAuditedEntity simpleAuditedEntity = new SimpleAuditedEntity("Emancipation reform of 1861");
            session.persist(simpleAuditedEntity);
            return simpleAuditedEntity;
        });

        doInTransaction(session -> {
            session.remove(newSimpleAuditedEntity);
        });

        doInTransaction(session -> {
            List<RevisionPojo> revisionPojos = HibernateUtils.selectAllRevisions(session, SimpleAuditedEntity.class);
            revisionPojos.sort(Comparator.naturalOrder());
            RevisionPojo revisionPojoOnDelete = revisionPojos.get(1);

            AuditReader auditReader = AuditReaderFactory.get(session);
            SimpleAuditedEntity simpleAuditedEntityFromRevision
                = auditReader.find(SimpleAuditedEntity.class, revisionPojoOnDelete.getId().longValue(), revisionPojoOnDelete.getRev());

            assertNull(simpleAuditedEntityFromRevision);
        });
    }

    @Entity(name = "SimpleAuditedEntity")
    @Table(name = "simple_audited_entity")
    @Audited
    @Data
    @NoArgsConstructor
    static class SimpleAuditedEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        private String data;

        SimpleAuditedEntity(String data) {
            this.data = data;
        }
    }
}
