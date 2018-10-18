package edu.collections;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HashMapTest {
    @SuppressWarnings("unchecked")
    @Test
    public void clone_WhenCloneHashMapAndThenPutAnotherEntryToClone_ThenOriginalHashMapDoesNotChanges() {
        HashMap<Integer, String> original = new HashMap<>();

        HashMap<Integer, String> clone = (HashMap<Integer, String>) original.clone();
        clone.put(1, "one");

        assertTrue(original.isEmpty());
    }

    @Test
    public void put_WhenPutSeveralEqualKeys_ThenLeaveLastPutKey() {
        Map<Integer, String> map = new HashMap<>();
        int key = 1;
        String firstValue = "one";
        map.put(key, firstValue);
        String secondValue = "two";
        map.put(key, secondValue);

        Set<Map.Entry<Integer, String>> entries = map.entrySet();

        assertEquals(1, entries.size());
        assertFalse(map.containsValue(firstValue));
        assertTrue(map.containsValue(secondValue));
    }
}
