package edu.collections;

import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class LinkedHashMapTest {

    @Test
    public void givenLinkedHashMap_whenGetsOrderedKeySet_thenSaveInsertionOrder() {
        LinkedHashMap<Integer, Object> map = new LinkedHashMap<>();
        map.put(0, null);
        map.put(1, null);
        map.put(2, null);
        map.put(3, null);
        map.put(4, null);

        Set<Integer> keys = map.keySet();
        Integer[] integers = keys.toArray(new Integer[0]);

        for (int i = 0; i < integers.length; i++) {
            assertEquals(new Integer(i), integers[i]);
        }
    }

    @Test
    public void givenLinkedHashMap_whenAccessOrderWorks_thenAccessedElementMovedToTheEndOfTheList() {
        LinkedHashMap<Integer, Object> map = new LinkedHashMap<>(16, .75F, true);
        map.put(0, null);
        map.put(1, null);
        map.put(2, null);
        map.put(3, null);
        map.put(4, null);

        Set<Integer> keys = map.keySet();
        assertEquals("[0, 1, 2, 3, 4]", keys.toString());

        map.get(0);
        assertEquals("[1, 2, 3, 4, 0]", keys.toString());

        map.get(4);
        assertEquals("[1, 2, 3, 0, 4]", keys.toString());

        map.get(2);
        assertEquals("[1, 3, 0, 4, 2]", keys.toString());

        map.get(1);
        assertEquals("[3, 0, 4, 2, 1]", keys.toString());
    }
}
