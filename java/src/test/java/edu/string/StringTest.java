package edu.string;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class StringTest {

    @Test
    public void substring() { //todo rename test
        String string = "foo:and:boo";

        int firstColonIndex = string.indexOf(":");
        String firstWord = string.substring(0, firstColonIndex);
        String secondWord = string.substring(firstColonIndex + 1);

        assertEquals("foo", firstWord);
        assertEquals("and:boo", secondWord);
    }

    @Test
    public void split1() { //todo rename test
        String string = "foo:and:boo";

        String[] parts = string.split(":", 2);

        assertEquals("foo", parts[0]);
        assertEquals("and:boo", parts[1]);
    }

    @Test
    public void split_DelimiterNotFound() {
        String string = "foo:and:boo";

        String[] parts = string.split(",");

        assertEquals(1, parts.length);
        assertEquals(string, parts[0]);
    }

    @Test
    public void split_KeepDelimiter() {
        String string = "+5five-10ten-2two";
        String[] split = string.split("(?=[+-])");
        System.out.println(Arrays.toString(split)); //todo add assertions
    }
}
