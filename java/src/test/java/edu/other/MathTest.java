package edu.other;

import org.junit.Assert;
import org.junit.Test;

public class MathTest {

    @Test
    public void log_WhenCompute_Then() {
        Assert.assertTrue(Double.isInfinite(Math.log(.0)));
        Assert.assertEquals(Double.NEGATIVE_INFINITY, Math.log(.0), .0);
    }
}
