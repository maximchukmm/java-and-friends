package edu.other;

public class ForCycleBorderDemo {
    public static void main(String[] args) {
        MyBorder border = new MyBorder(5);

        for (int i = 0; i < border.getBorder(); i++) {

        }

        System.out.println("\n----------------\n");

        for (int i = 0, n = border.getBorder(); i < n; i++) {

        }
    }
}

class MyBorder {
    private int border;

    MyBorder(int border) {
        this.border = border;
    }

    int getBorder() {
        System.out.println("getBorder() called");
        return border;
    }
}
