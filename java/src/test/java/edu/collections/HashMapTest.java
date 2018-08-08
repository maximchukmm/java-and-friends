package collections;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
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
        String lastValue = "two";
        map.put(key, lastValue);

        Set<Map.Entry<Integer, String>> entries = map.entrySet();

        assertEquals(key, entries.size());
        assertTrue(map.containsValue(lastValue));
    }
}
