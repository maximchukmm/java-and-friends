package net.projecteuler.problem03;

/**
 * The prime factors of 13195 are 5, 7, 13 and 29.
 * What is the largest prime factor of the number 600851475143?
 */

import static net.projecteuler.PrimeFactorsOfNumber.printPrimeFactorsOfNumber;

/**
 * Solution:
 * Almost brute force with some improvements.
 */

public class Problem03Solution01 {
    public static void main(String[] args) {
        printPrimeFactorsOfNumber(600851475143L);
        System.out.println("\nThe largest prime factor of the number 600851475143 is " + Solution01.solve(600851475143L));
    }
}

class Solution01 {
    static int solve(long number) {
        int maxPrimeFactor = 2;
        while (number % maxPrimeFactor == 0)
            number /= maxPrimeFactor;
        if (number == 1)
            return maxPrimeFactor;
        maxPrimeFactor = 1;

        while (number != 1) {
            maxPrimeFactor += 2;
            while (number % maxPrimeFactor == 0)
                number /= maxPrimeFactor;
        }

        return maxPrimeFactor;
    }
}
