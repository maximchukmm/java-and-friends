package edu.reflection.entityinfoextractor;

import edu.reflection.entityinfoextractor.uniqueconstraints.BaseEntity;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.lang.reflect.Field;

public class ClassInfoExtractor {
    public static String extractClassName(Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Clazz is null");
        }
        final String className = clazz.getSimpleName();
        return StringUtils.uncapitalize(className);
    }

    public static String extractUniqueColumnNames(BaseEntity entity) {
        if (entity.getClass().isAnnotationPresent(Table.class)) {
            return extractUniqueColumnNamesFromClass(entity);
        } else {
            return extractUniqueColumnNamesFromFields(entity);
        }
    }

    private static String extractUniqueColumnNamesFromFields(BaseEntity entity) {
        final Field[] declaredFields = entity.getClass().getDeclaredFields();
        StringBuilder builder = new StringBuilder();
        for (Field field : declaredFields) {
            final Column column = field.getAnnotation(Column.class);
            if (column.unique()) {
                builder.append(column.name() + "-");
            }
        }
        return builder.toString();
    }

    private static String extractUniqueColumnNamesFromClass(BaseEntity entity) {
        final Table table = entity.getClass().getAnnotation(Table.class);
        final UniqueConstraint[] uniqueConstraints = table.uniqueConstraints();
        if (uniqueConstraints.length > 0) {
            StringBuilder builder = new StringBuilder();
            for (UniqueConstraint uc : uniqueConstraints) {
                for (String columnName : uc.columnNames()) {
                    builder.append(columnName + "-");
                }
            }
            return builder.toString();
        }
        return null;
    }
}
