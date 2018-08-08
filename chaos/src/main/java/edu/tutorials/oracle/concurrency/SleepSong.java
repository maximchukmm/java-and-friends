package edu.tutorials.oracle.concurrency;

class SleepSong {
    static String[] song = {
        "Yesterday...",
        "All my troubles",
        "Seemed so far away.",
        "Now I need a place to hide away..."
    };

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < song.length; i++) {
            Thread.sleep(1000);
            System.out.println(song[i]);
        }
    }
}
