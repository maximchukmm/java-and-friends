package javapuzzlers.chapter01;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Puzzle 1: Oddity
 * The following method purports to determine whether its sole argument is an odd
 * number. Does the method work?
 * <br>
 * {@code
 * public static boolean isOdd(int i) {
 * return i % 2 == 1; }
 * }
 * <br>
 * The Java’s remainder operator ( % ) is defined to satisfy the following identity
 * for all int values a and all nonzero int values b :
 * <br>
 * (a / b) * b + (a % b) == a
 */
@RunWith(Parameterized.class)
public class Puzzle01_Oddity {
    private static boolean isOdd(int i) {
        //when i is negative odd integer, then method returns wrong result
        return i % 2 == 1;
    }

    private static boolean correctIsOdd(int i) {
        return i % 2 != 0;
    }

    private static boolean improvedIsOdd(int i) {
        return (i & 1) != 0;
    }

    @Parameters(name = "{index}: is {0} odd -> {1}")
    public static Iterable<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
            {-3, true},
            {-2, false},
            {-1, true},
            {0, false},
            {1, true},
            {2, false},
            {3, true}
        });
    }

    private int number;
    private boolean isNumberOdd;

    public Puzzle01_Oddity(int number, boolean isNumberOdd) {
        this.number = number;
        this.isNumberOdd = isNumberOdd;
    }

    @Test
    public void isOdd() {
        assertEquals(isNumberOdd, isOdd(number));
    }

    @Test
    public void correctIsOdd() {
        assertEquals(isNumberOdd, correctIsOdd(number));
    }

    @Test
    public void improvedIsOdd() {
        assertEquals(isNumberOdd, improvedIsOdd(number));
    }
}
