package edu.hibernate.softdelete;

import edu.hibernate.base.HibernateBaseTest;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.junit.Test;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.List;

import static edu.hibernate.util.HibernateUtils.selectAllJpql;
import static edu.hibernate.util.HibernateUtils.selectAllNative;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test based on article https://www.thoughts-on-java.org/implement-soft-delete-hibernate/
 */
public class SoftDeleteWithSimpleEntityTest extends HibernateBaseTest {
    @Override
    protected Class<?>[] entities() {
        return new Class[]{
            AccountWithDeleted.class,
            AccountWithoutDeleted.class
        };
    }

    @Test
    public void softDelete_WhenDeleteEntityWithSoftDelete_ThenJpaSelectAllDoesNotReturnThatDeletedEntity() {
        final String accountName = "savings";
        AccountWithDeleted account = new AccountWithDeleted(accountName);

        doInTransaction(session -> {
            session.persist(account);
        });
        doInTransaction(session -> {
            session.remove(account);
        });

        List<AccountWithDeleted> accounts = doInTransaction(session -> {
            return selectAllJpql(session, AccountWithDeleted.class);
        });

        assertTrue(accounts.isEmpty());
    }

    @Test
    public void softDelete_WhenDeleteEntityWithSoftDelete_ThenNativeSelectAllReturnThatDeletedEntity() {
        final String accountName = "savings";
        AccountWithDeleted account = new AccountWithDeleted(accountName);

        doInTransaction(session -> {
            session.persist(account);
        });
        doInTransaction(session -> {
            session.remove(account);
        });

        List<AccountWithDeleted> accounts = doInTransaction(session -> {
            return selectAllNative(session, AccountWithDeleted.class);
        });

        assertEquals(1, accounts.size());
        assertEquals(accountName, accounts.get(0).getName());
        assertTrue(accounts.get(0).isDeleted());
    }

    @Test
    public void delete_WhenDeleteEntityWithoutSoftDelete_ThenNativeSelectAllDoesNotReturnThatDeletedEntity() {
        final String accountName = "savings";
        AccountWithoutDeleted account = new AccountWithoutDeleted(accountName);

        doInTransaction(session -> {
            session.persist(account);
        });
        doInTransaction(session -> {
            session.remove(account);
        });

        List<AccountWithoutDeleted> accounts = doInTransaction(session -> {
            return selectAllNative(session, AccountWithoutDeleted.class);
        });

        assertTrue(accounts.isEmpty());
    }

    @Entity(name = "AccountWithDeleted")
    @Table(name = "account_with_deleted")
    @SQLDelete(sql = "UPDATE account_with_deleted SET deleted = true WHERE id = ?")
    @Where(clause = "deleted = false")
    @Data
    @NoArgsConstructor
    static class AccountWithDeleted {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id", updatable = false, nullable = false)
        private Long id;

        private String name;
        private boolean deleted;

        AccountWithDeleted(String name) {
            this.name = name;
            this.deleted = false;
        }
    }

    @Entity(name = "AccountWithoutDeleted")
    @Table(name = "account_without_deleted")
    @Data
    @NoArgsConstructor
    static class AccountWithoutDeleted {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id", updatable = false, nullable = false)
        private Long id;

        private String name;

        AccountWithoutDeleted(String name) {
            this.name = name;
        }
    }
}
