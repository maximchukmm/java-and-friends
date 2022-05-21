package edu.other;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureExperiments {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<?> future = executor.submit(new MyTask());

        System.out.println("isDone:" + future.isDone());
        System.out.println("isCancelled:" + future.isCancelled());

        while (!future.isDone() || future.isCancelled()) {
            System.out.println("Waiting for the task to be done...");
            sleepSeconds(1);
        }

        if (future.isDone()) {
            System.out.println("The task is done");
        } else {
            System.out.println("That is strange. The task isn't done");
        }

        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executor.shutdownNow();
    }

    private static class MyTask implements Runnable {
        @Override
        public void run() {
            System.out.println("Task is starting...");
            sleepSeconds(5);
            throwRuntimeException();
            System.out.println("Task is finished.");
        }

    }

    private static void sleepSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void throwRuntimeException() {
        throw new RuntimeException("Something wrong happened.");
    }

    private static class Container {
        private static final List<String> list = new ArrayList<>();

        public synchronized void addEntry(String s) {
            list.add(s);
        }

        public int size() {
            return list.size();
        }
    }
}
