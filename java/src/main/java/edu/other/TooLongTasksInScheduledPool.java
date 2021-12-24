package edu.other;

import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TooLongTasksInScheduledPool {
    private static volatile boolean isCompleted = false;
    private static final AtomicInteger counter = new AtomicInteger();

    public static void main(String[] args) {
        new Thread(() -> {
            sleep(15_000);
            isCompleted = true;
        }).start();

        ScheduledThreadPoolExecutor service = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(2);

        Runnable task = () -> {
            final int number = counter.incrementAndGet();

//            while (!isCompleted) {
            LocalDateTime now = LocalDateTime.now();

            System.out.printf("%s: %s -> %s\n", number, Thread.currentThread().getName(), now);

            sleep(10_000);
//            }
            System.out.printf("%s: %s -> %s\n", number, Thread.currentThread().getName(), isCompleted);
        };

        service.scheduleAtFixedRate(task, 0, 500, TimeUnit.MILLISECONDS);
        service.submit(task);
        service.submit(task);
        service.submit(task);
        service.submit(task);

        new Thread(() -> {
            for (int i = 0; i < 25; i++) {
                BlockingQueue<Runnable> queue = service.getQueue();
                System.out.println(queue.size());

                sleep(500);
            }
        }).start();

        try {
//            service.shutdown();
            service.shutdownNow();
            service.awaitTermination(20, TimeUnit.SECONDS);
            System.out.println("Executor is completed");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
