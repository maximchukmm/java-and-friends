package edu.numbers;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DifferentNumberClassesTest {

    @Test
    public void primitiveTypes_WhenFloatBringsIntoLong_ThenRemoveDecimalPartWithoutAnyRounding() {
        float floatValue = 9.9F;
        long longValue = (long) floatValue;

        assertEquals(9L, longValue);
    }
}
