package edu.hibernate.base;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Based on https://github.com/vladmihalcea/hibernate-master-class
 */
abstract public class HibernateBaseTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateBaseTest.class);

    protected SessionFactory sessionFactory;

    abstract protected Class<?>[] entities();

    protected Map<String, Object> additionalSettings() {
        return Collections.emptyMap();
    }

    protected void doInTransaction(TransactionVoidFunction function) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            function.accept(session);
            transaction.commit();
        } catch (Throwable e) {
            if (transaction != null && transaction.isActive()) transaction.rollback();
            LOGGER.error(e.toString());
            throw e;
        } finally {
            if (session != null)
                session.close();
        }
    }

    protected <T> T doInTransaction(TransactionFunction<T> function) {
        T result = null;
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            result = function.apply(session);
            transaction.commit();
        } catch (Throwable e) {
            if (transaction != null && transaction.isActive()) transaction.rollback();
            LOGGER.error(e.toString());
            throw e;
        } finally {
            if (session != null)
                session.close();
        }
        return result;
    }

    @Before
    public void setUp() {
        final StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
            .configure()
            .applySettings(additionalSettings())
            .build();

        try {
            MetadataSources metadataSources = new MetadataSources(standardRegistry);
            for (Class<?> clazz : entities())
                metadataSources.addAnnotatedClass(clazz);

            Metadata metadata = metadataSources
                .getMetadataBuilder()
                .enableNewIdentifierGeneratorSupport(true)
                .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
                .build();

            sessionFactory = metadata
                .getSessionFactoryBuilder()
                .build();
        } catch (Exception e) {
            LOGGER.error(e.toString());
            StandardServiceRegistryBuilder.destroy(standardRegistry);
        }
    }

    @After
    public void tearDown() {
        if (sessionFactory != null)
            sessionFactory.close();
    }

    @FunctionalInterface
    protected interface TransactionVoidFunction extends Consumer<Session> {
    }

    @FunctionalInterface
    protected interface TransactionFunction<T> extends Function<Session, T> {
    }
}
