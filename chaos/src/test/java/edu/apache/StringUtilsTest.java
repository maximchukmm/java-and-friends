package edu.apache;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringUtilsTest {

    @Test
    public void uncapitilizeTest() {
        assertEquals("string", StringUtils.uncapitalize("String"));
        assertEquals("integer", StringUtils.uncapitalize(Integer.class.getSimpleName()));
    }
}
