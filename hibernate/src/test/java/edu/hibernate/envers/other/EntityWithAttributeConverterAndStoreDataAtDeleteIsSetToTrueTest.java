package edu.hibernate.envers.other;

import edu.hibernate.base.HibernateBaseTest;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.hibernate.envers.configuration.EnversSettings;
import org.junit.Test;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

public class EntityWithAttributeConverterAndStoreDataAtDeleteIsSetToTrueTest extends HibernateBaseTest {
    @Override
    protected Class<?>[] entities() {
        return new Class<?>[]{
            EntityWithAttributeConverter.class,
        };
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Map additionalSettings() {
        Map additionalSettings = new HashMap();
        additionalSettings.put(EnversSettings.STORE_DATA_AT_DELETE, true);
        return additionalSettings;
    }

    @Test
    public void whenRemoveAuditedEntityWithBadAttributeConverterWithPropertyStoreDataAtDeleteSetToTrue_ThenDoesNotThrowAnyExpcetion() {
        EntityWithAttributeConverter createdEntity = doInTransaction(session -> {
            EntityWithAttributeConverter entity = new EntityWithAttributeConverter();
            entity.setEntityAttribute(EntityAttribute.VALUE_ONE);
            session.persist(entity);
            return entity;
        });

        doInTransaction(session -> {
            session.remove(createdEntity);
        });
    }

    @Entity(name = "EntityWithBadAttributeConverter")
    @Table(name = "entity_with_bad_attribute_converter")
    @Audited
    @Data
    @NoArgsConstructor
    static class EntityWithAttributeConverter {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @Basic(optional = false)
        @Convert(converter = EntityAttributeConverter.class)
        private EntityAttribute entityAttribute;
    }

    enum EntityAttribute {
        VALUE_ONE(1);

        private Integer value;

        EntityAttribute(int value) {
            this.value = value;
        }

        Integer getValue() {
            return value;
        }

        static EntityAttribute fromValue(int value) {
            if (value < 1 || value > 2) return null;

            switch (value) {
                case 1:
                    return VALUE_ONE;
                default:
                    throw new IllegalArgumentException("Unknown value: " + value);
            }
        }
    }


    public static class EntityAttributeConverter implements AttributeConverter<EntityAttribute, Integer> {
        @Override
        public Integer convertToDatabaseColumn(EntityAttribute attribute) {
            return attribute.getValue();
        }

        @Override
        public EntityAttribute convertToEntityAttribute(Integer dbData) {
            return EntityAttribute.fromValue(dbData);
        }
    }
}
