package edu.concurrency.concurrentread;

class ReadTaskDemo implements Runnable {
    private int indexOfResource;
    private int sleepMilliseconds;

    ReadTaskDemo(int indexOfResource, int sleepMilliseconds) {
        this.indexOfResource = indexOfResource;
        this.sleepMilliseconds = sleepMilliseconds;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(sleepMilliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(
            String.format("%s get access to resource \"%s\"",
                Thread.currentThread().getName(),
                ResourceDemo.getResource(indexOfResource)));
    }
}
