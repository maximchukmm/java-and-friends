package edu.concurrency;

public class VolatileExample {

    private static volatile boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Worker()).start();
        Thread.sleep(10);
        flag = false;
        System.out.println(flag);
    }

    public static class Worker implements Runnable {

        @Override
        public void run() {
            int i = 0;
            while (flag) {
                i++;
            }
            System.out.println(i);
        }
    }
}
