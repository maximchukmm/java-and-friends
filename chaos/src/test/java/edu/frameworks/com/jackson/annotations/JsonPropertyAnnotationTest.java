package edu.frameworks.com.jackson.annotations;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import edu.frameworks.com.jackson.base.JacksonBaseTest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import static edu.frameworks.com.jackson.util.JacksonUtils.toJson;
import static org.junit.Assert.assertEquals;

public class JsonPropertyAnnotationTest extends JacksonBaseTest {

    @Test
    public void whenSerializeObject_ThenUsePropertyNamesFromJsonPropertyAnnotation() throws Exception {
        String expected = toJson(
            "brand", "Lenovo",
            "year", 2014
        );

        String actual = objectMapper.writeValueAsString(new Notebook("Lenovo", 2014));

        assertEquals(expected, actual);
    }

    @Test
    public void whenDeserializeFromJsonWithPropertyNamesFromJsonPropertyAnnotation_ThenGetCorrectlyDeserializedInstance() throws Exception {
        Notebook expected = new Notebook("Lenovo", 2014);

        String json = toJson(
            "brand", "Lenovo",
            "year", 2014
        );
        Notebook actual = objectMapper.readValue(json, Notebook.class);

        assertEquals(expected, actual);
    }

    @Test(expected = UnrecognizedPropertyException.class)
    public void whenDeserializeFromJsonWithPropertyNamesEqualToFieldNames_ThenThrowUnrecognizedPropertyException() throws Exception {
        String json = toJson(
            "manufacturer", "Lenovo",
            "yearOfProduction", 2014
        );

        objectMapper.readValue(json, Notebook.class);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Notebook {
        @JsonProperty("brand")
        private String manufacturer;

        @JsonProperty("year")
        private int yearOfProduction;
    }
}
