package net.projecteuler;

public abstract class EulerUtils {
    private EulerUtils() {
        throw new IllegalArgumentException("Cannot create instance of util class.");
    }

    public static void printPrimeFactorsOfNumber(long number) {
        System.out.print(number + " = ");
        while (number % 2 == 0) {
            if (number == 2)
                System.out.print("2");
            else
                System.out.print("2 * ");
            number /= 2;
        }
        if (number == 1)
            return;

        int divisor = 3;
        while (number > 1) {
            while (number % divisor == 0) {
                if (number == divisor)
                    System.out.print(divisor);
                else
                    System.out.print(divisor + " * ");
                number /= divisor;
            }
            divisor += 2;
        }
    }

    public static long calculateArithmeticSum(long a1, long an, long n) {
        return ((a1 + an) * n) / 2;
    }
}
