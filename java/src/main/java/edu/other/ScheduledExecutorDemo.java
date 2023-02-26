package edu.other;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorDemo {
    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        Runnable task = () -> {
            System.out.printf("Started thread %s%n", Thread.currentThread().getName());
            sleepSeconds(2);
            System.out.printf("Finished thread %s%n", Thread.currentThread().getName());
        };

        executor.scheduleAtFixedRate(task, 0, 100, TimeUnit.MILLISECONDS);

        sleepSeconds(8);

        executor.shutdownNow();
    }

    private static void sleepSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
