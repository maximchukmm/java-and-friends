package edu.concurrency;

public class CountDownLatch {

    private int counter = 0;
    private final Object lock = new Object();

    public CountDownLatch(int counter) {
        this.counter = counter;
    }

    public void await() throws InterruptedException {
        synchronized (lock) {
            if (counter > 0) {
                lock.wait();
            }
        }
    }

    public void countDown() {
        synchronized (lock) {
            if (counter > 0) {
                counter--;
                System.out.println("Counter = " + counter);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (counter == 0) {
                lock.notifyAll();
            }
        }
    }

    int getCounter() {
        synchronized (lock) {
            return counter;
        }
    }
}
