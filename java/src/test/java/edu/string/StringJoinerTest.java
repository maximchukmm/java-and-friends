package edu.string;

import org.junit.Before;
import org.junit.Test;

import java.util.StringJoiner;

import static org.junit.Assert.assertEquals;

public class StringJoinerTest {

    private static final String DELIMITER = ":";
    private static final String EMPTY_VALUE = "empty";
    private static final String EMPTY_STRING = "";

    private StringJoiner stringJoiner;

    @Before
    public void setUp() {
        stringJoiner = new StringJoiner(DELIMITER);
        stringJoiner.setEmptyValue(EMPTY_VALUE);
    }

    @Test
    public void toString_WhenStringJoinerIsEmpty_ThenReturnEmptyString() {
        stringJoiner.setEmptyValue(EMPTY_STRING);
        String actualString = stringJoiner.toString();

        assertEquals(EMPTY_STRING, actualString);
    }

    @Test
    public void toString_WhenStringJoinerIsEmptyButEmptyValueWasSet_ThenReturnEmptySetValue() {
        String actualString = stringJoiner.toString();

        assertEquals(EMPTY_VALUE, actualString);
    }

    @Test
    public void toString_WhenAddOneStringToStringJoiner_ThenReturnThatStringWithoutDelimiter() {
        String value = "value";
        stringJoiner.add(value);

        String actualString = stringJoiner.toString();
        String expectedString = "value";

        assertEquals(expectedString, actualString);
    }

    @Test
    public void toString_WhenAddTwoStringsToStringJoiner_ThenReturnTwoStringSplitByDelimiter() {
        String value1 = "value1";
        String value2 = "value2";
        String expectedString = value1 + DELIMITER + value2;

        stringJoiner.add(value1);
        stringJoiner.add(value2);
        String actualString = stringJoiner.toString();

        assertEquals(expectedString, actualString);
    }

    @Test
    public void toString_WhenStringJoinerWithPrefixAndPostfix() {
        stringJoiner = new StringJoiner(":", "'", "'");

        String expected = "'value1:value2'";
        String actual = stringJoiner.add("value1").add("value2").toString();

        assertEquals(expected, actual);
    }
}
