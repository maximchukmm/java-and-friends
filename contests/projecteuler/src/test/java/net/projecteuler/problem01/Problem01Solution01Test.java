package net.projecteuler.problem01;

import org.junit.Assert;
import org.junit.Test;

public class Problem01Solution01Test {

    @Test
    public void solve() {
        //Find the sum of all the multiples of 3 or 5 below 1000.
        Assert.assertEquals(233_168, Problem01Solution01.solve(1000));
    }
}