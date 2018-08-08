package edu.concurrency.concurrentread;

import java.util.Random;

class ConcurrentReadMainDemo {
    public static void main(String[] args) {
        Random random = new Random();
        final int size = ResourceDemo.getResourceSize();

        for (int i = 64; i >= 0; i--) {
            ReadTaskDemo readTaskDemo = new ReadTaskDemo(random.nextInt(size), 10);
            (new Thread(readTaskDemo)).start();
        }
    }
}
