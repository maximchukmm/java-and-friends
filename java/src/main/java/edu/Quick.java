package edu;

public class Quick {
    public static void main(String[] args) {
        System.out.println("");
        System.out.println(" \"123\" ");
        System.out.println(" \"\"123\"\" ");
        System.out.println(" '123' ");
        System.out.println(" ''123'' ");
        System.out.println(" `123` ");
        System.out.println(" ``123`` ");

        System.out.println();

        System.out.println(Double.toString(2.0 - 1.1));
        System.out.println(Float.toString(2.0f - 1.1f));

        System.out.println();

        System.out.println(equals(null, null));
        Object a = null;
        Object b = null;
        System.out.println(equals(a, b));
    }

    // todo write tests for class Objects
    private static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }
}
