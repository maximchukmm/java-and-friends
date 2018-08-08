package edu.lambdaandstream;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

class ReferenceToMethod {
    public static void main(String[] args) {
//        MyContainer container = new MyContainer();
//
//        addString("str0", container::add);
//        addString("str1", container::add);
//        addString("str2", container::add);
//
//        System.out.println(container.list);

        MyAnotherContainer c = new MyAnotherContainer();

        addInteger(new Integer(555), (i) -> c.add(i));
        addInteger(new Integer(777), (i) -> c.add(i));
        addInteger(new Integer(888), (i) -> c.add(i));

        System.out.println(c.list);
    }

    private static void addString(String str, AddElement addElement) {
        addElement.add(str);
    }

    private static void addInteger(Integer integer, Consumer<Integer> consumer) {
        consumer.accept(integer);
    }

}

class MyContainer implements AddElement {
    List<String> list = new ArrayList<>();

    @Override
    public void add(String string) {
        list.add(string);
    }
}

interface AddElement {
    void add(String string);
}

class MyAnotherContainer {
    List<Integer> list = new ArrayList<>();

    public void add(Integer integer) {
        list.add(integer);
    }
}