package edu.other;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Random;

public class OptimizedBogosort {
    public static void main(String[] args) {
        int n = 5;

        int[] array = new int[n];

        fillArray(array);

        long begin = System.currentTimeMillis();

        long shuffleCounter = 0;
        int indexMax = n;

        while (!ArrayUtils.isSorted(array)) {
            shuffle(array, indexMax);
            //todo возвращать первый максимальный элемент слева, если правая часть уже отсортирована
//            indexMax = findIndexOfMaxValue(array);
            shuffleCounter++;
//            System.out.println(Arrays.toString(array));
        }

        long end = System.currentTimeMillis();

        System.out.println("time = " + (end - begin) / 1000.0);
        System.out.println("shuffleCounter = " + shuffleCounter);
    }

    private static void fillArray(int[] array) {
        for (int i = 0; i < array.length; i++)
            array[i] = i + 1;
        shuffle(array, array.length);
    }

    private static void shuffle(int[] array, int n) {
        if (n == 0) return;

        Random r = new Random(System.nanoTime());

        for (int i = 0; i < n; i++) {
            int i1 = r.nextInt(n);
            int i2 = r.nextInt(n);
            int temp = array[i1];
            array[i1] = array[i2];
            array[i2] = temp;
        }
    }

    private static int findIndexOfMaxValue(int[] array) {
        int maxIndex = 0;
        int maxValue = array[maxIndex];

        for (int i = 1; i < array.length; i++) {
            if (maxValue <= array[i]) {
                maxValue = array[i];
                maxIndex = i;
            }
        }

        return maxIndex;
    }
}
