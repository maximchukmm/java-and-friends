package edu.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class Print {

    public static <T extends Iterable> void map(Map<?, T> map) {
        for (Map.Entry<?, T> entry : map.entrySet()) {
            System.out.println("Key: " + entry.getKey());
            Iterator iterator = entry.getValue().iterator();
            iterator.forEachRemaining(System.out::println);
            System.out.println("--------");
        }
    }

    public static void collection(Collection collection) {
        System.out.println(collection);
    }

    public static void collection(Collection collection, String message) {
        System.out.println(message + " = " + collection);
    }

    public static void collectionLine(Collection collection) {
        collection.forEach(System.out::println);
    }
}
