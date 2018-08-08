package javapuzzlers.chapter01;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Puzzle 2: Time for a Change
 * Consider the following word problem:
 * Tom goes to the auto parts store to buy a spark plug that costs $1.10, but all he
 * has in his wallet are two-dollar bills. How much change should he get if he pays
 * for the spark plug with a two-dollar bill?
 * Here is a program that attempts to solve the word problem. What does it print?
 * <br>
 * <code>
 * public class Change {
 * public static void main(String args[]) {
 * System.out.println(2.00 - 1.10);
 * }
 * }
 * </code>
 */
public class Puzzle02_TimeForAChange {
    @Test
    public void notAllDecimalsCanBeRepresentedExactlyUsingBinaryFloatingPoint() {
        String expectedChange = "0.9";

        String actualChange = Double.toString(2.0 - 1.1);

        assertNotEquals(expectedChange, actualChange);
        assertEquals("0.8999999999999999", actualChange);
    }

    @Test
    public void whenCreateBigDecimalFromString_ThenReturnExactChange() {
        String expectedChange = "0.9";

        BigDecimal actualChange = new BigDecimal("2.0").subtract(new BigDecimal("1.1"));

        assertEquals(expectedChange, actualChange.toString());
    }

    @Test
    public void whenCreateBigDecimalFromDouble_ThenDoesNotReturnExactChange() {
        String expectedChange = "0.9";

        BigDecimal actualChange = new BigDecimal(2.0).subtract(new BigDecimal(1.1));

        assertNotEquals(expectedChange, actualChange.toString());
        assertEquals("0.899999999999999911182158029987476766109466552734375", actualChange.toString());
    }
}
