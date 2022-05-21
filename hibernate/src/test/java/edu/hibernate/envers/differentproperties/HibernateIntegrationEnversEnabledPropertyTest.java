package edu.hibernate.envers.differentproperties;

import edu.hibernate.base.HibernateBaseTest;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.junit.Assert;
import org.junit.Test;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Based on documentation
 * http://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#configurations-envers
 */
public class HibernateIntegrationEnversEnabledPropertyTest extends HibernateBaseTest {
    @Override
    protected Class<?>[] entities() {
        return new Class[]{
            SimpleEntityWithLongAndUniqueName.class
        };
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Map additionalSettings() {
        Map additionalSettings = new HashMap();
        additionalSettings.put("hibernate.integration.envers.enabled", false);
        return additionalSettings;
    }

    @Test
    public void whenEnversDisabled_ThenDoNotCreateAdditionalTablesForAuditingEntities() {
        doInTransaction(session -> {
            List audTableInfo = session
                .createNativeQuery("SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'SIMPLE_ENTITY_WITH_LONG_AND_UNIQUE_NAME_AUD'")
                .getResultList();

            Assert.assertTrue(audTableInfo.isEmpty());
        });
    }

    @Entity(name = "SimpleEntityWithLongAndUniqueName ")
    @Table(name = "simple_entity_with_long_and_unique_name ")
    @Audited
    @Data
    @NoArgsConstructor
    static class SimpleEntityWithLongAndUniqueName {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        private String data;

        SimpleEntityWithLongAndUniqueName(String data) {
            this.data = data;
        }
    }
}
