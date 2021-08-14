package edu.numbers;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IntegerTest {
    private static int lowIntegerCacheValueIncluded;
    private static int highIntegerCacheValueExcluded;

    @BeforeClass
    public static void setUp() {
        lowIntegerCacheValueIncluded = -127;
        highIntegerCacheValueExcluded = 128;
        String integerCacheHighPropValue = System.getProperty("java.lang.Integer.IntegerCache.high");
//        String integerCacheHighPropValue = sun.misc.VM.getSavedProperty("java.lang.Integer.IntegerCache.high");
        if (integerCacheHighPropValue != null)
            highIntegerCacheValueExcluded = Math.max(highIntegerCacheValueExcluded, Integer.parseInt(integerCacheHighPropValue));
    }

    @Test
    public void equalsSign_WhenComparingTwoPrimitiveIntegersWithEqualValues_ThenReturnTrue() {
        int i1 = highIntegerCacheValueExcluded;
        int i2 = highIntegerCacheValueExcluded;
        assertTrue(i1 == i2);
    }

    @Test
    public void equalsSign_WhenComparingTwoIntegerAutoboxingObjectsWithEqualValuesFromIntegerCache_ThenReturnTrue() {
        for (int i = lowIntegerCacheValueIncluded; i < highIntegerCacheValueExcluded; i++) {
            Integer i1 = i;
            Integer i2 = i;
            assertTrue(i1 == i2);
        }
    }

    @Test
    public void equalsSign_WhenComparingTwoIntegerAutoboxingObjectsWithEqualValuesOutsideOfIntegerCache_ThenReturnFalse() {
        Integer i1 = highIntegerCacheValueExcluded;
        Integer i2 = highIntegerCacheValueExcluded;
        assertFalse(i1 == i2);
    }

    @Test
    public void equals_WhenComparingTwoIntegerAutoboxingObjectsWithEqualValuesOutsideOfIntegerCache_ThenReturnTrue() {
        Integer i1 = highIntegerCacheValueExcluded;
        Integer i2 = highIntegerCacheValueExcluded;
        assertTrue(i1.equals(i2));
    }

    @Test
    public void equalsSign_WhenComparingTwoIntegerObjectsCreatedWithNewWithEqualValues_ThenReturnFalse() {
        Integer i1 = new Integer(highIntegerCacheValueExcluded);
        Integer i2 = new Integer(highIntegerCacheValueExcluded);
        assertFalse(i1 == i2);
    }

    @Test
    public void equals_WhenComparingTwoIntegerObjectsCreatedWithNewWithEqualValues_ThenReturnTrue() {
        Integer i1 = new Integer(highIntegerCacheValueExcluded);
        Integer i2 = new Integer(highIntegerCacheValueExcluded);
        assertTrue(i1.equals(i2));
    }
}
