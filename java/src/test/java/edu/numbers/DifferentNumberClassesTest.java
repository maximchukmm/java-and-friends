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

    @Test
    public void integer_WhenDividendLessDivisor_ThenReturnZero() {
        assertEquals(0, 0 / 60);
        assertEquals(0, 1 / 60);
        assertEquals(0, 29 / 60);
        assertEquals(0, 30 / 60);
        assertEquals(0, 31 / 60);
        assertEquals(0, 59 / 60);
    }

    @Test
    public void round_WhenDividendLessThanHalfOfDivisor_ThenReturnZero() {
        assertEquals(0, Math.round(29 / 60.0));
    }

    @Test
    public void round_WhenDividendLessThanDivisorButEqualOrGreaterThanHalfOfDivisor_ThenReturnOne() {
        assertEquals(1, Math.round(30 / 60.0));
        assertEquals(1, Math.round(31 / 60.0));
    }
}
