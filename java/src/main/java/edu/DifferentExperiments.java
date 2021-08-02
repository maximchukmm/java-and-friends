package edu;

import java.util.Random;

public class DifferentExperiments {
    public static void main(String[] args) {
        Random random = new Random(System.currentTimeMillis());

        int _00 = 0;
        int _01 = 0;
        int _10 = 0;
        int _11 = 0;

        int numberOfChecks = Integer.MAX_VALUE / 10;

        for (int i = 0; i < numberOfChecks; i++) {
            boolean first = random.nextBoolean();
            boolean second = random.nextBoolean();

            if (!first) {
                if (!second) {
                    _00++;
                } else {
                    _01++;
                }
            } else {
                if (!second) {
                    _10++;
                } else {
                    _11++;
                }
            }
        }

        System.out.println("00 = " + _00);
        System.out.println("01 = " + _01);
        System.out.println("10 = " + _10);
        System.out.println("11 = " + _11);

        System.out.println();

        System.out.println("00 = " + (_00 * 100.0) / numberOfChecks);
        System.out.println("01 = " + (_01 * 100.0) / numberOfChecks);
        System.out.println("10 = " + (_10 * 100.0) / numberOfChecks);
        System.out.println("11 = " + (_11 * 100.0) / numberOfChecks);
    }
}
