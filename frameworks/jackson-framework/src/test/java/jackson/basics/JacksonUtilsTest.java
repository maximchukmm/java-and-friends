package jackson.basics;

import org.junit.Test;

import static jackson.util.JacksonUtils.toJson;
import static org.junit.Assert.assertEquals;

public class JacksonUtilsTest {

    @Test(expected = IllegalArgumentException.class)
    public void toJson_WhenCallWithoutParameters_ThenThrowIllegalArgumentException() {
        toJson();
    }

    @Test(expected = IllegalArgumentException.class)
    public void toJson_WhenCallWithOddNumberOfParameters_ThenThrowIllegalArgumentException() {
        toJson(
            "propertyOne", "valueOne",
            "propertyTwo"
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void toJson_WhenGenerateJsonWithEmptyPropertyName_ThenThrowIllegalArgumentException() {
        toJson("", "valueOne");
    }

    @Test(expected = IllegalArgumentException.class)
    public void toJson_WhenGenerateJsonWithNullPropertyName_ThenThrowIllegalArgumentException() {
        toJson(null, "valueOne");
    }

    @Test
    public void toJson_WhenGenerateJsonWithOnePairOfCorrectParameters_ThenReturnCorrectJson() {
        String expected = "{\"property\":\"value\"}";

        String actual = toJson("property", "value");

        assertEquals(expected, actual);
    }

    @Test
    public void toJson_WhenGenerateJsonWithTwoPairsOfCorrectParameters_ThenReturnCorrectJson() {
        String expected = "{\"propertyOne\":\"valueOne\",\"propertyTwo\":\"valueTwo\"}";

        String actual = toJson(
            "propertyOne", "valueOne",
            "propertyTwo", "valueTwo"
        );

        assertEquals(expected, actual);
    }
}
