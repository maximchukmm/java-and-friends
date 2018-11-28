package edu.other;

import org.junit.Assert;
import org.junit.Test;

public class OperatorsTest {
    @Test
    public void bitwiseExclusiveOr_withBooleanValues() {
        Assert.assertFalse(false ^ false);
        Assert.assertTrue(false ^ true);
        Assert.assertTrue(true ^ false);
        Assert.assertFalse(true ^ true);
    }
}
