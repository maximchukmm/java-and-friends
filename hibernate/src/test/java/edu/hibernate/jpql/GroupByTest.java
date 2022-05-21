package edu.hibernate.jpql;

import edu.hibernate.base.HibernateBaseTest;
import lombok.Data;
import org.junit.Before;
import org.junit.Test;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GroupByTest extends HibernateBaseTest {
    @Override
    protected Class<?>[] entities() {
        return new Class[]{
            MyData.class
        };
    }

    @Test
    @SuppressWarnings("unchecked")
    public void whenGroupByWithoutSpecifyingResultClass_ThenReturnListOfArrayOfObjects() {
        doInTransaction(session -> {
            List<Object[]> resultList = (List<Object[]>) session.createQuery(
                "select m.title, count(*) " +
                    "from MyData m " +
                    "where m.flag = true " +
                    "group by m.title")
                .getResultList();

            Map<String, Long> result = new HashMap<>();

            for (Object[] objects : resultList) {
                result.put((String) objects[0], (Long) objects[1]);
            }

            assertEquals(3, resultList.size());
            assertEquals(5L, (long) result.get("title2"));
        });
    }

    @Test
    public void whenGroupByWithProjections_ThenReturnListOfProjections() {
        doInTransaction(session -> {
            List<MyDataDTO> resultList = session.createQuery(
                "select new edu.hibernate.jpql.MyDataDTO(m.title, count(*)) " +
                    "from MyData m " +
                    "where m.flag = true " +
                    "group by m.title", MyDataDTO.class)
                .getResultList();

            assertEquals(3, resultList.size());
            assertTrue(resultList.contains(new MyDataDTO("title3", 3L)));
        });
    }

    @Before
    public void prepareData() {
        doInTransaction(session -> {
            for (int i = 1; i <= 3; i++) {
                for (int j = 1; j <= 10; j++) {
                    session.persist(new MyData("title" + i, j % i == 0, j));
                }
            }
        });
    }

    @Entity(name = "MyData")
    @Table(name = "my_data")
    @Data
    static class MyData {
        MyData(String title, boolean flag, int number) {
            this.title = title;
            this.flag = flag;
            this.number = number;
        }

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @Basic(optional = false)
        @Column(name = "title")
        private String title;

        @Basic(optional = false)
        @Column(name = "flag")
        private boolean flag;

        @Basic(optional = false)
        @Column(name = "number")
        private int number;
    }
}
