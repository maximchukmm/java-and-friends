package net.projecteuler;

public class PrimeFactorsOfNumber {
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
        final long BORDER = (long)Math.sqrt(number);
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
}
