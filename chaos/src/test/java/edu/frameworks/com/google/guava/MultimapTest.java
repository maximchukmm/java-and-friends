package edu.frameworks.com.google.guava;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.junit.Test;

import java.util.Collection;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MultimapTest {
    @Test
    public void put_WhenAddTwoEqualKeys_ThenSaveThemBothInMap() {
        Multimap<Integer, String> map = HashMultimap.create();
        Integer key1 = 1;
        String value1 = "один";
        Integer key2 = 1;
        String value2 = "one";
        map.put(key1, value1);
        map.put(key2, value2);

        assertEquals(2, map.size());

        Collection<String> values = map.get(key1);
        assertEquals(2, values.size());
        assertTrue(values.contains(value1));
        assertTrue(values.contains(value2));
    }

    @Test
    public void putAll() {
        Multimap<Integer, String> map1 = HashMultimap.create();
        Integer map1Key = 1;
        String map1Value = "один";
        map1.put(map1Key, map1Value);

        Multimap<Integer, String> map2 = HashMultimap.create();
        Integer map2Key = 1;
        String map2Value = "one";
        map2.put(map2Key, map2Value);

        boolean isMap1Changed = map1.putAll(map2);

        assertTrue(isMap1Changed);
        assertEquals(2, map1.size());

        Set<Integer> keySet = map1.keySet();
        assertEquals(1, keySet.size());
        assertTrue(keySet.contains(map1Key));

        Collection<String> values = map1.get(map1Key);
        assertEquals(2, values.size());
        assertTrue(values.contains(map1Value));
        assertTrue(values.contains(map2Value));
    }
}
