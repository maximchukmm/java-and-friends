package edu.frameworks.com.jackson.basics;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import edu.frameworks.com.jackson.base.JacksonBaseTest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Assert;
import org.junit.Test;

import static edu.frameworks.com.jackson.basics.EnumFieldSerializationDeserializationTest.Word.Letter.A;
import static edu.frameworks.com.jackson.basics.EnumFieldSerializationDeserializationTest.Word.Letter.N;
import static edu.frameworks.com.jackson.util.JacksonUtils.toJson;

public class EnumFieldSerializationDeserializationTest extends JacksonBaseTest {

    @Test
    public void whenDeserializeJsonWithEnumNamesAsPropertiesValuesInUpperCase_ThenReturnCorrectInstance() throws Exception {
        String json = toJson(
            "first", "A",
            "second", "N",
            "third", "N"
        );

        Word expected = new Word(A, N, N);
        Word actual = objectMapper.readValue(json, Word.class);

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = InvalidFormatException.class)
    public void whenDeserializeJsonWithEnumNamesAsPropertiesValuesInLowerCase_ThenThrowInvalidFormatException() throws Exception {
        String json = toJson(
            "first", "A",
            "second", "N",
            "third", "n"
        );

        objectMapper.readValue(json, Word.class);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Word {
        private Letter first;
        private Letter second;
        private Letter third;

        enum Letter {
            A, N
        }
    }
}
