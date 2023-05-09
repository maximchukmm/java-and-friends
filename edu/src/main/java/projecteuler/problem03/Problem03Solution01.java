package projecteuler.problem03;

/**
 * The prime factors of 13195 are 5, 7, 13 and 29.
 * What is the largest prime factor of the number 600851475143?
 */

import static projecteuler.EulerUtils.printPrimeFactorsOfNumber;

/**
 * Solution:
 * Almost brute force with some improvements.
 */
//todo check solution via unit-tests
public class Problem03Solution01 {
    public static void main(String[] args) {
        long number = 600851475143L;

        printPrimeFactorsOfNumber(number);

        System.out.println("\nThe largest prime factor of the number 600851475143 is " + Solution01.solve(number));
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
