package edu.hibernate.lombok.gettersetter;

import edu.hibernate.base.HibernateBaseTest;
import edu.hibernate.lombok.gettersetter.entity.EntityWithPrivateGetterAndSetter;
import edu.hibernate.lombok.gettersetter.entity.EntityWithoutGetterAndSetter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GetterSetterTest extends HibernateBaseTest {
    @Override
    protected Class<?>[] entities() {
        return new Class<?>[]{
            EntityWithPrivateGetterAndSetter.class,
            EntityWithoutGetterAndSetter.class
        };
    }

    @Test
    public void whenPersistEntityWithPrivateGetterAndSetterForOneField_ThenInsertThatEntityToDatabaseCorrectly() {
        doInTransaction(session -> {
            EntityWithPrivateGetterAndSetter entity = new EntityWithPrivateGetterAndSetter();
            entity.setPublicData("public data");
            session.persist(entity);
        });
    }

    @Test
    public void whenSelectEntityWithPrivateGetterAndSetterForOneField_ThenGetEntityFromDatabaseCorrectly() {
        Long idFromDB = doInTransaction(session -> {
            EntityWithPrivateGetterAndSetter entity = new EntityWithPrivateGetterAndSetter();
            entity.setPublicData("public data");
            session.persist(entity);
            return entity.getId();
        });

        doInTransaction(session -> {
            EntityWithPrivateGetterAndSetter entity = session.find(EntityWithPrivateGetterAndSetter.class, idFromDB);

            assertEquals("public data", entity.getPublicData());
        });
    }

    @Test
    public void whenPersistEntityWithoutGetterAndSetterForOneField_ThenInsertThatEntityToDatabaseCorrectly() {
        doInTransaction(session -> {
            session.persist(new EntityWithoutGetterAndSetter("public data"));
        });
    }

    @Test
    public void whenSelectEntityWithoutGetterAndSetterForOneField_ThenGetThatEntityFromDatabaseCorrectly() {
        Long idFromDB = doInTransaction(session -> {
            EntityWithoutGetterAndSetter entity = new EntityWithoutGetterAndSetter("public data");
            session.persist(entity);
            return entity.getId();
        });

        doInTransaction(session -> {
            EntityWithoutGetterAndSetter entity = session.find(EntityWithoutGetterAndSetter.class, idFromDB);

            assertEquals("public data", entity.getPublicData());
        });
    }
}
