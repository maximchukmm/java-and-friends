package edu.arrays;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class ArraysTest {

    @Test
    public void copyOfRange_whenCopyRangeOfArray_ThenRightBorderIsExclusive() {
        Assert.assertArrayEquals(
            new int[]{4, 5, 6},
            Arrays.copyOfRange(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8}, 4, 7)
        );
    }

    @Test
    public void copyOf_whenCopy_Then() {
        Assert.assertArrayEquals(
            new int[]{0, 1, 2},
            Arrays.copyOf(new int[]{0, 1, 2, 3, 4, 5, 6}, 3)
        );
    }
}
