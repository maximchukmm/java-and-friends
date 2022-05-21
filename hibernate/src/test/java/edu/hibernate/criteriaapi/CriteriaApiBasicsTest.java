package edu.hibernate.criteriaapi;

import edu.hibernate.base.HibernateBaseTest;
import edu.hibernate.util.HibernateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Before;
import org.junit.Test;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Based on articles
 * <p>
 * https://vladmihalcea.com/jpa-criteria-api-bulk-update-delete/
 * <p>
 * https://www.thoughts-on-java.org/criteria-updatedelete-easy-way-to/
 * <p>
 */
public class CriteriaApiBasicsTest extends HibernateBaseTest {
    @Override
    protected Class<?>[] entities() {
        return new Class<?>[]{
            BasicData.class
        };
    }

    @Before
    public void prepareBasicData() {
        doInTransaction(session -> {
            session.persist(new BasicData(1L, 1));
            session.persist(new BasicData(2L, 2));
            session.persist(new BasicData(3L, 3));
            session.persist(new BasicData(4L, 4));
            session.persist(new BasicData(5L, 5));
        });
    }

    @Test
    public void findMaxInteger() {
        int expectedMaxInteger = 5;

        int actualMaxInteger = doInTransaction(session -> {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Integer> findMaxQuery = builder.createQuery(Integer.class);
            Root<BasicData> root = findMaxQuery.from(BasicData.class);
            findMaxQuery.select(builder.max(root.get("integer")));
            return session.createQuery(findMaxQuery).getSingleResult();
        });

        assertEquals(expectedMaxInteger, actualMaxInteger);
    }

    @Test
    public void lessThanOrEqualTo() {
        List<BasicData> expected = new ArrayList<>();
        expected.add(new BasicData(1L, 1));
        expected.add(new BasicData(2L, 2));

        List<BasicData> actual = doInTransaction(session -> {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<BasicData> query = builder.createQuery(BasicData.class);
            Root<BasicData> root = query.from(BasicData.class);
            query
                .select(root)
                .where(builder.lessThanOrEqualTo(root.get("integer"), 2));

            return session.createQuery(query).getResultList();
        });

        assertEquals(expected.size(), actual.size());
        assertTrue(expected.containsAll(actual));
    }

    @Test
    public void whenSelectByNonExistingIntegerValue_ThenGetResultListMethodReturnsEmptyList() {
        doInTransaction(session -> {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<BasicData> query = builder.createQuery(BasicData.class);
            Root<BasicData> root = query.from(BasicData.class);
            query
                .select(root)
                .where(builder.equal(root.get("integer"), 777));

            List<BasicData> list = session.createQuery(query).getResultList();

            assertTrue(list.isEmpty());
        });
    }

    @Test
    public void in_WhenIdHasIntegerTypeButUsingCollectionOfStringIds_ThenCreateCorrectQueryAndReturnListOfEntities() {
        doInTransaction(session -> {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<BasicData> query = builder.createQuery(BasicData.class);
            Root<BasicData> root = query.from(BasicData.class);
            query
                .select(root)
                .where(
                    root.get("id").in(Arrays.asList("1", "2", "3"))
                );

            List<BasicData> list = session.createQuery(query).getResultList();

            assertEquals(3, list.size());
        });
    }

    @Test
    public void criteriaBuilder_AvgAndNullIf_WhenNullIfReturnNull_ThenAvgDo() {
        doInTransaction(session -> {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Double> query = builder.createQuery(Double.class);
            Root<BasicData> root = query.from(BasicData.class);
            query.select(builder.avg(builder.nullif(root.get("integer"), 2)));

            Double actualAverage = session.createQuery(query).getSingleResult();
            Double expectedAverage = 3.25;

            assertEquals(expectedAverage, actualAverage);
        });
    }

    @Test
    public void criteriaUpdate_WhenWithoutWhereClause_ThenUpdateAllEntities() {
        doInTransaction(session -> {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaUpdate<BasicData> criteriaUpdate = builder.createCriteriaUpdate(BasicData.class);
            Root<BasicData> root = criteriaUpdate.from(BasicData.class);

            criteriaUpdate.set(root.get("integer"), 0);
            session.createQuery(criteriaUpdate).executeUpdate();
        });

        doInTransaction(session -> {
            List<BasicData> allEntities = HibernateUtils.selectAllJpql(session, BasicData.class);

            List<BasicData> entitiesWithNonZeroInteger = allEntities
                .stream()
                .filter(entity -> entity.getInteger() != 0)
                .collect(Collectors.toList());

            assertTrue(entitiesWithNonZeroInteger.isEmpty());
        });
    }

    @Test
    public void criteriaUpdate_WhenFilterBySpecificEntities_ThenUpdateOnlyThatEntities() {
        doInTransaction(session -> {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<BasicData> query = builder.createQuery(BasicData.class);
            Root<BasicData> root = query.from(BasicData.class);

            query.select(root)
                .where(root.get("id").in(Arrays.asList(2L, 3L, 4L)));

            List<BasicData> specificEntities = session.createQuery(query).getResultList();

            CriteriaUpdate<BasicData> update = builder.createCriteriaUpdate(BasicData.class);
            Root<BasicData> rootUpdate = update.from(BasicData.class);
            update
                .set(rootUpdate.get("integer"), 0)
                .where(rootUpdate.in(specificEntities));

            session.createQuery(update).executeUpdate();
        });

        doInTransaction(session -> {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<BasicData> query = builder.createQuery(BasicData.class);
            Root<BasicData> root = query.from(BasicData.class);

            query.select(root)
                .where(root.get("id").in(Arrays.asList(2L, 3L, 4L)));

            List<BasicData> updatedEntities = session.createQuery(query).getResultList();

            List<BasicData> entitiesWithNonZeroInteger = updatedEntities
                .stream()
                .filter(entity -> entity.getInteger() != 0)
                .collect(Collectors.toList());

            assertTrue(entitiesWithNonZeroInteger.isEmpty());
        });
    }

    @Entity(name = "BasicData")
    @Table(name = "basic_data")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class BasicData {
        @Id
        private Long id;

        private int integer;
    }
}
