package edu.hibernate.criteriaapi;

import edu.hibernate.base.HibernateBaseTest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    static class DoubleResult {

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
