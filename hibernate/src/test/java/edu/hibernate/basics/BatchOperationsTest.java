package edu.hibernate.basics;

import edu.hibernate.base.HibernateBaseTest;
import edu.hibernate.util.HibernateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class BatchOperationsTest extends HibernateBaseTest {
    private static int batchSize = 15;
    private static int entityCount = 40;

    @Override
    protected Class<?>[] entities() {
        return new Class[]{
            ArithmeticProgression.class
        };
    }

    @Override
    protected Map<String, Object> additionalSettings() {
        Map<String, Object> additionalSettings = new HashMap<>();
        additionalSettings.put("hibernate.jdbc.batch_size", batchSize);
        additionalSettings.put("hibernate.order_inserts", true);
        additionalSettings.put("hibernate.order_updates", true);
        return additionalSettings;
    }

    @Test
    public void whenPersistenceContextSizeIsEqualToBatchSize_ThenFlushCache() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();

        try {
            transaction.begin();

            for (int i = 0; i < entityCount; i++) {
                if (i % batchSize == 0) {
                    session.flush();
                    session.clear();
                }

                ArithmeticProgression value = new ArithmeticProgression(i + 1);
                session.persist(value);
            }

            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    @Test
    public void whenPersistenceContextSizeIsEqualToBatchSize_ThenCommitTransactionAndStartANewOne() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();

        try {
            transaction.begin();
            for (int i = 0; i < entityCount; i++) {
                if (i % batchSize == 0) {
                    transaction.commit();
                    transaction.begin();
                    session.clear();
                }
                ArithmeticProgression value = new ArithmeticProgression(i + 1);
                session.persist(value);
            }
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    @Test
    public void whenInsertSeveralEntitiesInSingleQuery_ThenSelectAllReturnThatNumberOfEntities() {
        int numberOfInserts = doInTransaction(session -> {
            String batchInsert = "INSERT INTO arithmetic_progression(id, value) VALUES " +
                "(1, 10), " +
                "(2, 15), " +
                "(3, 20), " +
                "(4, 25), " +
                "(5, 30)";
            return session.createNativeQuery(batchInsert).executeUpdate();
        });

        doInTransaction(session -> {
            List<ArithmeticProgression> values = HibernateUtils.selectAllJpql(session, ArithmeticProgression.class);

            assertEquals(numberOfInserts, values.size());
        });
    }

    @Entity(name = "ArithmeticProgression")
    @Table(name = "arithmetic_progression")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class ArithmeticProgression {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        private Integer value;

        ArithmeticProgression(Integer value) {
            this.value = value;
        }
    }
}
