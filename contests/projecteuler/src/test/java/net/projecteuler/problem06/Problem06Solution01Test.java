package net.projecteuler.problem06;

import org.junit.Assert;
import org.junit.Test;

public class Problem06Solution01Test {

    @Test
    //The difference between the sum of the squares of the first ten natural numbers and the square of the sum is 2640
    public void solve_CheckExample() {
        Assert.assertEquals(2640, Problem06Solution01.solve(10));
    }

    @Test
    public void checkAnswer() {
        Assert.assertEquals(25164150, Problem06Solution01.solve(100));
    }
}