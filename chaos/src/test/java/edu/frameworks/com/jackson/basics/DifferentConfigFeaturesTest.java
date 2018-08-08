package edu.frameworks.com.jackson.basics;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import edu.frameworks.com.jackson.base.JacksonBaseTest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import static edu.frameworks.com.jackson.util.JacksonUtils.toJson;

public class DifferentConfigFeaturesTest extends JacksonBaseTest {

    @Test(expected = UnrecognizedPropertyException.class)
    public void readValue_WhenDeserializeJsonWithUnknownPropertyAndFailOnUnknownPropertiesFeatureEnabled_ThenThrowUnrecognizedPropertyException() throws Exception {
        objectMapper.enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        String json = toJson(
            "author", "Kostrikin A.",
            "title", "Introduction to Algebra",
            "pages", 340,
            "unknownField", "unknownValue"
        );

        Book book = objectMapper.readValue(json, Book.class);
    }

    @Test
    public void readValue_WhenDeserializeJsonWithUnknownPropertyAndFailOnUnknownPropertiesFeatureDisabled_ThenDoesNotThrowException() throws Exception {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        String json = toJson(
            "author", "Kostrikin A.",
            "title", "Introduction to Algebra",
            "pages", 340,
            "unknownField", "unknownValue"
        );

        Book book = objectMapper.readValue(json, Book.class);
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Book {
        private String author;
        private String title;
        private int pages;
    }
}
