package edu.rabbitmq;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author mmaximchuk
 * @since 08.07.2021
 */
public class CreditProducerDemoTest {
    @Test
    public void doubleFormattingTest() {
        double value = 1.245678;

        String actual = String.format("%.2f", value);

        Assert.assertEquals("1.25", actual);
    }
}