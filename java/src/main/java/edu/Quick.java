package edu;

import java.util.List;

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

//        String delim = "\\|";
//        String str1 = "||";
//        String str2 = "|||";
//
//        System.out.println(str2.substring(0, 0));
//        System.out.println(str2.substring(0, 0).length());
//        System.out.println(Arrays.toString(str1.substring(1).split(delim)));
//        System.out.println(Arrays.toString(str2.substring(1).split(delim)));

//        boolean[] booleans = new boolean[2];
//        System.out.println(Arrays.toString(booleans));

//        System.out.println(false | false);
//        System.out.println(false | true);
//        System.out.println(true | false);
//        System.out.println(true | true);

//        boolean b = false;
//        b |= true;
//        System.out.println(b);
//        b |= false;
//        System.out.println(b);

//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2019, Calendar.APRIL, 7);
//        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
//        System.out.println(dayOfWeek);
//        System.out.println(Calendar.SUNDAY);

        //todo write tests for these cases
//        List<Integer> list = Arrays.asList(8, 2, 7, 11, 2, 5, 7, 11);
//        int a = list.stream().reduce(Integer.MIN_VALUE, (x, y) -> x > y ? x : y);
//        int b = list.stream().max((x, y) -> x > y ? x : y).get();
//        int c = list.parallelStream().reduce(8, (x, y) -> x > y ? x : y);
//        int d = list.stream().reduce(11, (x, y) -> x < y ? x : y);
//        int e = list.stream().max(Integer::compare).get();
//        int f = list.stream().max(Integer::max).get();
//
//        System.out.println(a);
//        System.out.println(b);
//        System.out.println(c);
//        System.out.println(d);
//        System.out.println(e);
//        System.out.println(f);

//        double money = 1.123;
//        NumberFormat USA = NumberFormat.getCurrencyInstance(Locale.US);
//        NumberFormat India = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
//        NumberFormat China = NumberFormat.getCurrencyInstance(Locale.CHINA);
//        NumberFormat France = NumberFormat.getCurrencyInstance(Locale.FRANCE);
//
//        System.out.println(USA.format(money));
//        System.out.println(India.format(money));
//        System.out.println(China.format(money));
//        System.out.println(France.format(money));
//
//        System.out.println(Arrays.toString(Locale.getAvailableLocales()));
//        System.out.println(Arrays.toString(Locale.getISOCountries()));

        String abc = "abc";
        System.out.println(abc.substring(0, 1).toUpperCase() + abc.substring(1));
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
