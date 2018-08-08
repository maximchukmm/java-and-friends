package edu.concurrency;

public class ThreadsSynchronizedRun implements Runnable {
    private int x;
    private int y;

    public static void main(String[] args) throws Exception {
        ThreadsSynchronizedRun that = new ThreadsSynchronizedRun();
        (new Thread(that)).start();
        (new Thread(that)).start();
    }

    public synchronized void run() {
        for (int i = 0; i < 4; ++i) {
            x++;
            y++;
            System.out.println("x = " + x + ", y = " + y);
        }
    }
}
