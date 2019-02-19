package edu.hibernate.envers.other;

import edu.hibernate.base.HibernateBaseTest;
import edu.hibernate.util.HibernateUtils;
import edu.hibernate.util.RevisionPojo;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RevisionType;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AuditingBulkOperationsTest extends HibernateBaseTest {
    @Override
    protected Class<?>[] entities() {
        return new Class<?>[]{
            Transport.class
        };
    }

    @Before
    public void prepareData() {
        doInTransaction(session -> {
            session.persist(new Transport("car", 25));
            session.persist(new Transport("bicycle", 1000));
            session.persist(new Transport("motorbike", 350));
            session.persist(new Transport("train", 10));
            session.persist(new Transport("airplane", 15));
            session.persist(new Transport("skateboard", 2000));
            session.persist(new Transport("roller skates", 150));
        });
    }

    @Test
    public void whenExecutingBulkUpdate_ThenEntitiesAreNotAudited() {
        doInTransaction(session -> {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaUpdate<Transport> criteriaUpdate = builder.createCriteriaUpdate(Transport.class);
            Root<Transport> root = criteriaUpdate.from(Transport.class);

            criteriaUpdate
                .set(root.get("number"), 0)
                .set(root.get("rented"), true)
                .where(
                    builder.or(
                        builder.equal(root.get("type"), "car"),
                        builder.equal(root.get("type"), "motorbike")
                    )
                );

            session.createQuery(criteriaUpdate).executeUpdate();
        });

        doInTransaction(session -> {
            List<RevisionPojo> modRevisions =
                HibernateUtils.selectAllRevisionsWithRevisionType(session, Transport.class, RevisionType.MOD);

            assertTrue(modRevisions.isEmpty());
        });
    }

    @Test
    public void whenUpdateEntitiesOneByOne_ThenEntitiesAreAudited() {
        doInTransaction(session -> {
            List<Transport> entities = HibernateUtils.selectAllJpql(session, Transport.class)
                .stream()
                .filter(transport -> transport.getType().equals("car") || transport.getType().equals("motorbike"))
                .collect(Collectors.toList());

            entities.forEach(e -> {
                e.setNumber(0);
                e.setRented(true);
            });
        });

        doInTransaction(session -> {
            List<RevisionPojo> modRevisions =
                HibernateUtils.selectAllRevisionsWithRevisionType(session, Transport.class, RevisionType.MOD);

            assertEquals(2, modRevisions.size());
        });
    }

    @Entity(name = "Transport")
    @Table(name = "transport")
    @Audited
    @Data
    @NoArgsConstructor
    static class Transport {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;

        private String type;
        private int number;
        private boolean rented;

        Transport(String type, int number) {
            this.type = type;
            this.number = number;
        }
    }
}
