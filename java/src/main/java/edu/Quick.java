package edu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

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
//        String str2CopyThroughNew = new String("str2");ja
//
//        System.out.println("str1 = " + System.identityHashCode(str1));
//        System.out.println("str1Copy = " + System.identityHashCode(str1Copy));
//
//        System.out.println();
//
//        System.out.println("str2 = " + System.identityHashCode(str2));
//        System.out.println("str2CopyThroughNew = " + System.identityHashCode(str2CopyThroughNew));

//        System.out.println(1 + 2 + "3" + 4 + 5);

//        Quick quick = new Quick();
//        quick.new InnerClass();

//        int[][] arrInt2Dim = new int[2][2];
//        for (int[] ints : arrInt2Dim) {
//            for (int v : ints) {
//                System.out.println(v);
//            }
//        }

//        int[][][] arrayC = {{{0, 1}, {2, 3}, {4, 5}}};

//        int n = 10;
//        System.out.println(n--);
//        System.out.println(--n);
//        System.out.println(n);
//
//        try {
//            n--;
//        } catch (Exception e) {
//            System.out.println();
//        }

//        int _ = 0;1

//        List<String> strings = new ArrayList<>();
//        findFirstIndex(strings, "1");

//        String[] strings = new String[2];
//        strings[0] = "0";
//        strings[1] = "1";
//
//        Iterator<String> iterator = Arrays.stream(strings).iterator();
//        while (iterator.hasNext()) {
//            System.out.println(iterator.next());
//        }

//        List<Integer> integers = new ArrayList<>();
//        Iterator<Integer> iterator = integers.iterator();
//        System.out.println(iterator.hasNext());

//        Number n1 = myGenericFunction(1, 2.0);
//        System.out.println(n1);
//
//        Double n2 = myGenericFunction(1.0, 2.0);
//        System.out.println(n2);
//
//        Integer n3 = myGenericFunction(1, 2);
//        System.out.println(n3);
//
//        Number n4 = myGenericFunction(1, 2L);
//        System.out.println(n4);

//        List<Integer> list = new ArrayList<>();
//        list.add(1);
//        list.add(2);
//        list.add(3);
//        list.add(4);
//        list.add(5);
//
//        Collections.shuffle(list);
//
//        System.out.println(list);

//        System.out.println(new UUID(-100, -100));
//        System.out.println(new UUID(-100, -100));
//        System.out.println(new UUID(-100, -100).equals(new UUID(-100, -100)));
    }

    static <T> T myGenericFunction(T v1, T v2) {
        return v1;
    }

    String foo(Integer i, String s) {
        return "1";
    }

    String foo(Integer i, Object o) {
        return "2";
    }

    public static <E, T extends List<E>> int findFirstIndex(T list, E elem) {
        return -1;
    }

    private static int staticOuterInt = 0;

    private int nonStaticOuterInt = 1;

    class InnerClass {
        private static final int staticField = 0;

        private int number = 0;

        InnerClass() {
            System.out.println(staticOuterInt);
        }

        Quick getOuterClass() {
            return Quick.this;
        }
    }

    class ExtendedInnerClass extends InnerClass {

    }

    void myMethod() {
        class IsItInnerClass {
            IsItInnerClass() {
                System.out.println(nonStaticOuterInt);
            }
        }
    }

    // todo write tests for class Objects
    private static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }
}
