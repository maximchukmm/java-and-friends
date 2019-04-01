package edu.string;

import org.junit.Assert;
import org.junit.Test;

public class StringFormatTest {

    @Test
    public void whenWithZeroFlag_ThenAddAdditionalZerosIfNecessary() {
        Assert.assertEquals("05 d., 01:05", String.format("%02d d., %02d:%02d", 5, 1, 5));
    }
}
