package edu.hibernate.other;

import edu.hibernate.base.HibernateBaseTest;
import edu.hibernate.util.HibernateUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.junit.Test;

import javax.persistence.*;
import java.util.List;

import static org.junit.Assert.assertFalse;

public class UseDbProviderReservedKeywordForTableNameInSqlDeleteAndWhereAnnotationsTest extends HibernateBaseTest {
    @Override
    protected Class<?>[] entities() {
        return new Class[]{
            Order.class,
            OrderInfo.class
        };
    }

    @Test
    public void whenReservedKeywordEnclosedWithinBackticks_ThenSuccessfullyGeneratingScheme() {
        doInTransaction(session -> {
            Order order = new Order();
            OrderInfo orderInfo = new OrderInfo("Ordered by Armavir");
            order.addOrderInfo(orderInfo);
            session.persist(order);
            session.persist(orderInfo);
        });
    }

    @Test
    public void whenReservedKeywordEnclosedWithinBackticks_ThenSuccessfullyPersistingEntity() {
        doInTransaction(session -> {
            Order order = new Order();
            OrderInfo orderInfo = new OrderInfo("Ordered by Armavir");
            order.addOrderInfo(orderInfo);
            session.persist(order);
            session.persist(orderInfo);
        });
    }

    @Test(expected = PersistenceException.class)
    public void whenTryingToRemoveEntityWithReservedKeywordInTableName_ThenThrowException() {
        Long id = doInTransaction(session -> {
            Order order = new Order();
            OrderInfo orderInfo = new OrderInfo("Ordered by Armavir");
            order.addOrderInfo(orderInfo);
            session.persist(order);
            session.persist(orderInfo);
            return order.getId();
        });

        doInTransaction(session -> {
            Order order = session.find(Order.class, id);
            session.remove(order);
        });
    }

    @Test(expected = PersistenceException.class)
    public void whenTryingToSelectEntitiesWithWhereAnnotaionWhereTableNameFromReservedKeywordIsUsed_ThenThrowException() {
        doInTransaction(session -> {
            Order order = new Order();
            OrderInfo orderInfo = new OrderInfo("Ordered by Armavir");
            order.addOrderInfo(orderInfo);
            session.persist(order);
            session.persist(orderInfo);
        });

        doInTransaction(session -> {
            List<OrderInfo> orderInfos = HibernateUtils.selectAllJpa(session, OrderInfo.class);

            assertFalse(orderInfos.isEmpty());
        });
    }

    @Entity(name = "Order")
    @Table(name = "`order`")
    @SQLDelete(sql = "UPDATE order SET closed = true WHERE id = ?")
    @Data
    @NoArgsConstructor
    static class Order {
        @Id
        @GeneratedValue
        private Long id;

        boolean closed;

        @OneToOne(mappedBy = "order")
        private OrderInfo orderInfo;

        void addOrderInfo(OrderInfo orderInfo) {
            this.orderInfo = orderInfo;
            orderInfo.setOrder(this);
        }
    }

    @Entity(name = "OrderInfo")
    @Table(name = "order_info")
    @Where(clause = "false = (SELECT o.closed FROM `order` o WHERE o.id = order_id)")
    @Data
    @NoArgsConstructor
    static class OrderInfo {
        @Id
        @GeneratedValue
        private Long id;

        private String info;

        @OneToOne(optional = false)
        @JoinColumn(name = "order_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_order_id"))
        private Order order;

        OrderInfo(String info) {
            this.info = info;
        }
    }
}
