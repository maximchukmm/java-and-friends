package edu.hibernate.util;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.envers.RevisionType;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

abstract public class HibernateUtils {
    public static <ENTITY> List<ENTITY> selectAllJpql(Session session, Class<ENTITY> clazz) {
        String selectAllJpql = "from " + getEntityName(clazz);
        return session.createQuery(selectAllJpql, clazz).getResultList();
    }

    private static String getEntityName(Class<?> clazz) {
        Entity entityAnnotation = clazz.getDeclaredAnnotation(Entity.class);
        String entityName = entityAnnotation.name();
        if (!StringUtils.isEmpty(entityName)) return entityName;
        return clazz.getSimpleName();
    }

    public static <ENTITY> List<ENTITY> selectAllNative(Session session, Class<ENTITY> clazz) {
        String selectAllNative = "SELECT * FROM " + getTableName(clazz);
        return session.createNativeQuery(selectAllNative, clazz).getResultList();
    }

    @SuppressWarnings("unchecked")
    public static List<RevisionPojo> selectAllRevisions(Session session, Class<?> clazz) {
        String selectAllRevisions = "SELECT " +
            "id, REV, REVTYPE " +
            "FROM " + getTableName(clazz) + "_AUD";
        List<Object[]> revisions = session.createNativeQuery(selectAllRevisions).getResultList();
        return convertToRevisionPojo(revisions);
    }

    @SuppressWarnings("unchecked")
    public static List<RevisionPojo> selectAllRevisionsWithRevisionType(Session session, Class<?> clazz, RevisionType revisionType) {
        String selectAllRevisions = "SELECT " +
            "id, REV, REVTYPE " +
            "FROM " + getTableName(clazz) + "_AUD " +
            "WHERE REVTYPE = " + revisionType.getRepresentation();
        List<Object[]> revisions = session.createNativeQuery(selectAllRevisions).getResultList();
        return convertToRevisionPojo(revisions);
    }

    private static List<RevisionPojo> convertToRevisionPojo(List<Object[]> revisions) {
        List<RevisionPojo> revisionPojos = new ArrayList<>(revisions.size());
        for (Object[] revision : revisions) {
            RevisionPojo revisionPojo = new RevisionPojo();
            revisionPojo.setId((BigInteger) revision[0]);
            revisionPojo.setRev((Integer) revision[1]);
            revisionPojo.setRevType(RevisionType.fromRepresentation(revision[2]));
            revisionPojos.add(revisionPojo);
        }
        return revisionPojos;
    }

    private static <ENTITY> String getTableName(Class<ENTITY> clazz) {
        Table tableAnnotation = clazz.getDeclaredAnnotation(Table.class);
        String tableName = tableAnnotation.name();
        if (StringUtils.isEmpty(tableName))
            throw new RuntimeException(String.format("Entity %s without explicitly declared table name", clazz.getSimpleName()));
        return tableName;
    }

    public static <ENTITY> ENTITY selectByIdNative(Session session, Class<ENTITY> clazz, Object id) {
        return session.createNativeQuery("SELECT * FROM " + getTableName(clazz) + " WHERE id = ?1", clazz)
            .setParameter(1, id)
            .getSingleResult();
    }
}
