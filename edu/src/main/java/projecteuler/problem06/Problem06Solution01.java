package projecteuler.problem06;

/*
 * The sum of the squares of the first ten natural numbers is,
 *
 * 12 + 22 + ... + 102 = 385
 * The square of the sum of the first ten natural numbers is,
 *
 * (1 + 2 + ... + 10)2 = 552 = 3025
 * Hence the difference between the sum of the squares of the first ten natural numbers and the square of the sum
 * is 3025 − 385 = 2640.
 *
 * Find the difference between the sum of the squares of the first one hundred natural numbers and the square of the sum.
 */

import static projecteuler.EulerUtils.arithmeticSum;

/*
 * Solution:
 * S1 = 1^2 + ... + n^2
 * S2 = (1 + ... + n)^2
 *
 * S2 = 1^2 + (1*2 + 1*3 + ... + 1*(n-1) + 1*n)
 *    + 2^2 + (2*1 + 2*3 + ... + 2*(n-1) + 2*n)
 * ...+ n^2 + (n*1 + n*2 + ... + n*(n-2) + n*(n-1)) =
 *
 *    = 1^2 + 2^2 + ... + n^2 +
 *    + (1*2 + 1*3 + 1*4 + ... + 1*(n-1) + 1*n) +
 *    + (2*1 + 2*3 + 2*4 + ... + 2*(n-1) + 2*n) +
 *    + (3*1 + 3*2 + 3*4 + ... + 3*(n-1) + 3*n) +
 * ...+ (n*1 + n*2 + n*3 + ... + n*(n-2) + n*(n-1)) =
 *
 *    = S1 +
 *    + 2 * (1*2 + 1*3 + 1*4 + ... + 1*(n-1) + 1*n) +
 *    + 2 * (2*3 + 2*4 + 2*5 + ... + 2*(n-1) + 2*n) +
 *    + 2 * (3*4 + 3*5 + 3*6 + ... + 3*(n-1) + 3*n) +
 * ...+ 2 * ((n-2) * (n-1) + (n-2) + n)
 *    + 2 * ((n-1) * n) =
 *
 *    = S1 +
 *    + 2 * (
 *    + 1 * (2 + 3 + ... + n) +
 *    + 2 * (3 + 4 + ... + n) +
 *    + 3 * (4 + 5 + ... + n) +
 * ...+ (n-2) * (n-1 + n) +
 *    + (n-1) * (n) +
 *    + n * (0)
 *    )
 *
 *    Is int type enough for calculations?
 *    2^10 = 1024 > 10^3 = 1000
 *
 *    (1 + 2 + ... + 100)^2 = 5050^2 = (5000 + 50)^2 = 5000^2 + 2*50*5000 + 50^2 =
 *    = 25*10^6 + 5*10^5 + 25*10^2 =
 *    = 1/2 * (5 * 10^7 + 10^6 + 5*10^3) =
 *    = 1/2 * (51 * 10^6 + 5*10^3) < 1/2 (51 * 2^20 + 5 * 2^10) =
 *    = 51 * 2^19 + 2^9 < 2^25 + 2^9 < 2^26
 *
 *    max positive int = 2^31 - 1 => int is enough
 */
public class Problem06Solution01 {

    public static int solve(int n) {
        int sum = (int) arithmeticSum(1, n, n);

        int result = 0;

        for (int i = 1; i < n; i++) {
            sum -= i;
            result += i * sum;
        }

        return result * 2;
    }
}
