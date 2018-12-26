package edu;

import java.util.Arrays;

public class Quick {
    public static void main(String[] args) {
//        System.out.println("");
//        System.out.println(" \"123\" ");
//        System.out.println(" \"\"123\"\" ");
//        System.out.println(" '123' ");
//        System.out.println(" ''123'' ");
//        System.out.println(" `123` ");
//        System.out.println(" ``123`` ");
//
//        System.out.println();
//
//        System.out.println(Double.toString(2.0 - 1.1));
//        System.out.println(Float.toString(2.0f - 1.1f));
//
//        System.out.println();
//
//        System.out.println(equals(null, null));
//        Object a = null;
//        Object b = null;
//        System.out.println(equals(a, b));

        //todo tests for string pool and string intern() method
//        String str1 = "str1";
//        String str2 = "str2";
//        String str1Copy = "str1";
//        String str2CopyThroughNew = new String("str2");
//
//        System.out.println("str1 = " + System.identityHashCode(str1));
//        System.out.println("str1Copy = " + System.identityHashCode(str1Copy));
//
//        System.out.println();
//
//        System.out.println("str2 = " + System.identityHashCode(str2));
//        System.out.println("str2CopyThroughNew = " + System.identityHashCode(str2CopyThroughNew));
    }

    // todo write tests for class Objects
    private static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }
}
