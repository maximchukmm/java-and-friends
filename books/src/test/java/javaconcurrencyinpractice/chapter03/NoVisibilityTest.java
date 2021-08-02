package javaconcurrencyinpractice.chapter03;

import org.junit.Test;

public class NoVisibilityTest {
    @Test
    public void noVisibilityTest() {
        new ReaderThread().start();
        number = 43;
        ready = true;
    }

    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread {
        @Override
        public void run() {
            while (!ready) {
                Thread.yield();
            }
            System.out.println(number);
        }
    }
}
