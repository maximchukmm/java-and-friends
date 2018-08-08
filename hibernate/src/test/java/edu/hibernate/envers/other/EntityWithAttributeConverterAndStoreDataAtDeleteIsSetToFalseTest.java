package edu.hibernate.envers.other;

import edu.hibernate.base.HibernateBaseTest;
import edu.hibernate.util.HibernateUtils;
import edu.hibernate.util.RevisionPojo;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.AssertionFailure;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.Audited;
import org.junit.Test;

import javax.persistence.*;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertNull;

public class EntityWithAttributeConverterAndStoreDataAtDeleteIsSetToFalseTest extends HibernateBaseTest {
    @Override
    protected Class<?>[] entities() {
        return new Class<?>[]{
            EntityWithBadAttributeConverter.class,
            EntityWithGoodAttributeConverter.class
        };
    }

    /**
     * Это исключение возникает из-за свойства org.hibernate.envers.store_data_at_delete (default: false )
     * Should the entity data be stored in the revision when the entity is deleted (instead of only storing the id and all other properties as null).
     * (from http://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#envers)
     */
    @Test(expected = AssertionFailure.class)
    public void whenRemoveAuditedEntityWithBadAttributeConverterWithPropertyStoreDataAtDeleteSetToDefault_ThenThrowAssertionFailureException() {
        EntityWithBadAttributeConverter createdEntity = doInTransaction(session -> {
            EntityWithBadAttributeConverter entity = new EntityWithBadAttributeConverter();
            entity.setEntityAttribute(EntityAttribute.VALUE_ONE);
            session.persist(entity);
            return entity;
        });

        doInTransaction(session -> {
            session.remove(createdEntity);
        });
    }

    @Test
    public void whenRemoveAuditedEntityWithGoodAttributeConverter_ThenDoesNotThrowAnyException() {
        EntityWithGoodAttributeConverter createdEntity = doInTransaction(session -> {
            EntityWithGoodAttributeConverter entity = new EntityWithGoodAttributeConverter();
            entity.setEntityAttribute(EntityAttribute.VALUE_ONE);
            session.persist(entity);
            return entity;
        });

        doInTransaction(session -> {
            session.remove(createdEntity);
        });
    }

    @Test
    public void whenRemoveAuditedEntityWithGoodAttributeConverter_ThenFindEntityByRevisionNumberWithRevisionTypeDelReturnNull() {
        EntityWithGoodAttributeConverter createdEntityWithGoodAttributeConverter = doInTransaction(session -> {
            EntityWithGoodAttributeConverter entityWithGoodAttributeConverter = new EntityWithGoodAttributeConverter();
            entityWithGoodAttributeConverter.setEntityAttribute(EntityAttribute.VALUE_THREE);
            session.persist(entityWithGoodAttributeConverter);
            return entityWithGoodAttributeConverter;
        });

        doInTransaction(session -> {
            session.remove(createdEntityWithGoodAttributeConverter);
        });

        doInTransaction(session -> {
            List<RevisionPojo> revisionPojos = HibernateUtils.selectAllRevisions(session, EntityWithGoodAttributeConverter.class);
            revisionPojos.sort(Comparator.naturalOrder());
            RevisionPojo revisionPojoOnDelete = revisionPojos.get(1);

            AuditReader auditReader = AuditReaderFactory.get(session);
            EntityWithGoodAttributeConverter revisionedEntityWithGoodAttributeConverter
                = auditReader.find(EntityWithGoodAttributeConverter.class, revisionPojoOnDelete.getId().longValue(), revisionPojoOnDelete.getRev());

            assertNull(revisionedEntityWithGoodAttributeConverter);
        });
    }

    @Entity(name = "EntityWithBadAttributeConverter")
    @Table(name = "entity_with_bad_attribute_converter")
    @Audited
    @Data
    @NoArgsConstructor
    static class EntityWithBadAttributeConverter {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @Basic(optional = false)
        @Convert(converter = BadEntityAttributeConverter.class)
        private EntityAttribute entityAttribute;
    }

    @Entity(name = "EntityWithGoodAttributeConverter")
    @Table(name = "entity_with_good_attribute_converter")
    @Audited
    @Data
    @NoArgsConstructor
    static class EntityWithGoodAttributeConverter {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @Basic(optional = false)
        @Convert(converter = GoodEntityAttributeConverter.class)
        private EntityAttribute entityAttribute;
    }

    enum EntityAttribute {
        VALUE_ZERO(0),
        VALUE_ONE(1),
        VALUE_TWO(2),
        VALUE_THREE(3);

        private Integer value;

        EntityAttribute(int value) {
            this.value = value;
        }

        Integer getValue() {
            return value;
        }

        static EntityAttribute fromValue(int value) {
            if (value < 0 || value > 3) return null;

            switch (value) {
                case 0:
                    return VALUE_ZERO;
                case 1:
                    return VALUE_ONE;
                case 2:
                    return VALUE_TWO;
                case 3:
                    return VALUE_THREE;
                default:
                    throw new IllegalArgumentException("Unknown value: " + value);
            }
        }
    }

    public static class GoodEntityAttributeConverter implements AttributeConverter<EntityAttribute, Integer> {
        @Override
        public Integer convertToDatabaseColumn(EntityAttribute attribute) {
            if (attribute == null) return null;
            return attribute.getValue();
        }

        @Override
        public EntityAttribute convertToEntityAttribute(Integer dbData) {
            return EntityAttribute.fromValue(dbData);
        }
    }

    public static class BadEntityAttributeConverter implements AttributeConverter<EntityAttribute, Integer> {
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
