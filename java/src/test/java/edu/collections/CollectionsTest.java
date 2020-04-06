package edu.collections;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CollectionsTest {

    @Test
    public void frequency() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 3, 3, 3, 4, 5, 6);

        Assert.assertEquals(4, Collections.frequency(numbers, 3));
    }
}
