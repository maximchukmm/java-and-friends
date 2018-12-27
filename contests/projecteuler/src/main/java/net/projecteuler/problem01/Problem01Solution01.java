package net.projecteuler.problem01;

/*
 Multiples of 3 and 5
 If we list all the natural numbers below 10 that are multiples of 3 or 5, we get 3, 5, 6 and 9.
 The sum of these multiples is 23.
 Find the sum of all the multiples of 3 or 5 below 1000.
*/

/*
* Solution:
* n - натуральное число
* k3 = (n - c3)/3, где c3 = 3, если n % 3 == 0, иначе c3 = n % 3
* k5 = (n - c5)/5, где c5 = 5, если n % 5 == 0, иначе c5 = n % 5
* k15 = (n - c15)/15, где c15 = 15, если n % 15 == 0, иначе c15 = n % 15
* Решением задачи является результат суммы
* ResultSum = Sum(3i) + Sum(5j) - Sum(15l), i=1..k3, j=1..k5, l=1..k15
* Sum(15l) - вычитаем делители, повторяющиеся в суммах Sum(3i) и Sum(5j)
* После применения формулы суммы арифметической прогрессии
* получим окончательную формулу для нахождения нашей суммы:
* (5*k5 * (k5 + 1) + 3*k3 * (k3 + 1) - 15*k15 * (k15 + 1))/2
* */


public class Problem01Solution01 {
    public static void main(String[] args) {
        System.out.println(solve(1000));
    }

    static long solve(long n) {
        final long k3 = (n - 1) / 3;
        final long k5 = (n - 1) / 5;
        final long k15 = (n - 1) / 15;
        return calculateArithmeticSum(3, 3 * k3, k3)
                + calculateArithmeticSum(5, 5 * k5, k5)
                - calculateArithmeticSum(15, 15 * k15, k15);
    }

    private static long calculateArithmeticSum(long a1, long an, long n) {
        return ((a1 + an) * n) / 2;
    }
}
