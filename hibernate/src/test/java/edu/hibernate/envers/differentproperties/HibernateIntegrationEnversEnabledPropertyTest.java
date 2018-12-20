package edu.hibernate.envers.differentproperties;

import edu.hibernate.base.HibernateBaseTest;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.*;
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
            SimpleEntity.class
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
                .createNativeQuery("SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'SIMPLE_ENTITY_AUD'")
                .getResultList();

            Assert.assertTrue(audTableInfo.isEmpty());
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
