package edu.frameworks.com.jackson.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract public class JacksonUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(JacksonUtils.class);

    public static String toJson(Object... objects) {
        checkInputArguments(objects);
        return generateJsonString(objects);
    }

    private static String generateJsonString(Object[] objects) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        for (int i = 0; i < objects.length - 2; i += 2) {
            addPropertyName(json, objects[i]);
            addPropertyValueWithComma(json, objects[i + 1]);
        }
        for (int i = objects.length - 2; i < objects.length; i += 2) {
            addPropertyName(json, objects[i]);
            addPropertyValueWithoutComma(json, objects[i + 1]);
        }
        json.append("}");
        return json.toString();
    }

    private static void addPropertyName(StringBuilder json, Object propertyName) {
        json.append("\"")
            .append(propertyName)
            .append("\":");
    }

    private static void addPropertyValueWithComma(StringBuilder json, Object propertyValue) {
        addPropertyValue(json, propertyValue);
        json.append(",");
    }

    private static void addPropertyValueWithoutComma(StringBuilder json, Object propertyValue) {
        addPropertyValue(json, propertyValue);
    }

    private static void addPropertyValue(StringBuilder json, Object propertyValue) {
        if (propertyValue instanceof Number) {
            json.append(propertyValue);
        } else {
            json.append("\"");
            json.append(propertyValue);
            json.append("\"");
        }
    }

    private static void checkInputArguments(Object[] objects) {
        if (objects.length == 0) {
            LOGGER.error("Trying to generate json string from empty array.");
            throw new IllegalArgumentException("Cannot generate json string from empty array.");
        }
        if (objects.length % 2 != 0) {
            LOGGER.error("Cannot generate json string from array with odd length: " + objects.length);
            throw new IllegalArgumentException("Trying to generate json string from array with odd length: " + objects.length);
        }
        for (int i = 0; i < objects.length; i += 2) {
            if (objects[i] == null) {
                LOGGER.error("Trying to generate json string with null property name.");
                throw new IllegalArgumentException("Cannot generate json string with null property name.");
            } else if (!(objects[i] instanceof String)) {
                LOGGER.error("Trying to generate json string with non-string property name: " + objects[i].getClass().getSimpleName());
                throw new IllegalArgumentException("Cannot generate json string with non-string property name: " + objects[i].getClass().getSimpleName());
            } else {
                String propertyName = (String) objects[i];
                if (propertyName.isEmpty()) {
                    LOGGER.error("Trying to generate json string with empty property name.");
                    throw new IllegalArgumentException("Cannot generate json string with empty property name.");
                }
            }
        }
    }
}
