package edu.concurrency;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SynchronizedExample {

    private static int counter;
    private final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        final SynchronizedExample synchronizedExample = new SynchronizedExample();
        synchronizedExample.test();
        System.out.println(synchronizedExample.counter);
    }

    public static int increment() {
        synchronized (SynchronizedExample.class) {
            return counter++;
        }
    }

    // public static synchronized int increment() {
    //     return counter++;
    // }

    // public synchronized int increment() {
    //     synchronized (this) {
    //         return counter++;
    //     }
    // }

    // public synchronized int increment() {
    //     synchronized (lock) {
    //         return counter++;
    //     }
    // }

    // public synchronized int increment() {
    //     return counter++;
    // }

    public void test() throws InterruptedException {
        List<Aggregator> aggregators = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            final Aggregator aggregator = new Aggregator();
            aggregators.add(aggregator);
            new Thread(aggregator).start();
        }

        Thread.sleep(100);

        Set<Integer> integerSet = new HashSet<>();
        for (Aggregator aggregator : aggregators) {
            for (Integer anInt : aggregator.ints) {
                if (!integerSet.add(anInt)) {
                    System.out.println("Error, duplicate found: " + anInt);
                }
            }
        }
    }


    public class Aggregator implements Runnable {

        private List<Integer> ints = new ArrayList<>();

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                ints.add(increment());
            }
        }
    }
}
