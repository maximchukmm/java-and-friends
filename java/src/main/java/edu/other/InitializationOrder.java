package edu.other;

import java.util.ArrayList;
import java.util.List;

public class InitializationOrder {
    public static void main(String[] args) {
        new MyInitializationOrderClass("string");
    }
}


class MyInitializationOrderClass {
    private static Integer integer = getInteger(10);

    static {
        System.out.println("static block initialization 01");
    }

    static {
        System.out.println("static block initialization 02");
    }

    static {
        System.out.println("static block initialization 03");
    }

    private List<Integer> list;

    {
        System.out.println("block initialization 01");
    }

    {
        System.out.println("block initialization 02");
        list = new ArrayList<>(10);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
    }

    {
        System.out.println("block initialization 03");
    }

    private String string;


    MyInitializationOrderClass(String string) {
        System.out.println("constructor");
        System.out.println(list.size());
        this.string = string;
    }

    private List<Integer> getList(int size) {
        System.out.println("getList");
        return new ArrayList<>(size);
    }

    private static Integer getInteger(int integer) {
        System.out.println("static getInteger");
        return new Integer(integer);
    }
}