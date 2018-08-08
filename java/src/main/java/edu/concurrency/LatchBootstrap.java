package edu.concurrency;

public class LatchBootstrap {

    private CountDownLatch countDownLatch;

    public static void main(String[] args) {
        new LatchBootstrap().test();
    }

    public void test() {
        countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(new Worker()).start();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (countDownLatch.getCounter() > 0) {
                    countDownLatch.countDown();
                }
            }
        }).start();
    }

    public class Worker implements Runnable {

        @Override
        public void run() {
            System.out.println("Thread " + Thread.currentThread().getName() + " start waiting");
            try {
                countDownLatch.await();
                Thread.sleep(250);
                System.out.println("Thread " + Thread.currentThread().getName() + " stop waiting");
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
