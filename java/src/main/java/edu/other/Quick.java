package edu.other;

public class Quick {
    public static void main(String[] args) {
        MyClass mc = new MyClass(1, 2, "3");

        System.out.println(customMethod(mc, "4"));
    }

    private static final class MyClass {
        private int i;
        private Integer ii;
        private String s;

        public MyClass(int i, Integer ii, String s) {
            this.i = i;
            this.ii = ii;
            this.s = s;
        }

        private MyClass() {
            // no initialization
        }

        public int getI() {
            return i;
        }

        public Integer getIi() {
            return ii;
        }

        private String getS() {
            return s;
        }

        @Override
        public String toString() {
            return "MyClass{" +
                    "i=" + i +
                    ", ii=" + ii +
                    ", s='" + s + '\'' +
                    '}';
        }
    }

    private static String customMethod(MyClass mc, String str) {
        return mc.toString() + ", " + str;
    }
}
