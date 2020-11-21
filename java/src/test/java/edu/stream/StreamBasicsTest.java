package edu.stream;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StreamBasicsTest {
    @Test
    public void givenEmptyList_WhenCollectingElementsOfEmptyList_ThenCreateEmptyList() {
        List<Integer> emptyList = Collections.emptyList();

        List<Integer> actualList = emptyList
            .stream()
            .collect(Collectors.toList());
        List<Integer> expectedList = Collections.emptyList();

        assertEquals(expectedList.size(), actualList.size());
    }

    @Test
    public void givenListOfStrings_WhenReduceToSingleStringByConcatenating_ThenReturnSingleString() {
        String expectedSingleString = "1234";

        String actualSingleString = Arrays
            .stream(new String[]{"1", "2", "3", "4"})
            .reduce("", (s1, s2) -> s1 + s2);

        assertEquals(expectedSingleString, actualSingleString);
    }

    @Test
    public void givenListOfIntegersWithDuplicateValues_WhenUsingDistinctMethod_ThenReturnListWithoutDuplicateValues() {
        List<Integer> withDuplicates = new ArrayList<>();
        withDuplicates.add(1);
        withDuplicates.add(1);
        withDuplicates.add(2);
        withDuplicates.add(2);
        withDuplicates.add(3);
        withDuplicates.add(3);

        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        List<Integer> actual = withDuplicates
            .stream()
            .distinct()
            .collect(Collectors.toList());

        assertEquals(expected.size(), actual.size());
        assertTrue(expected.containsAll(actual));
    }

    @Test
    public void givenEmptyListOfIntegers_WhenFindSumOfList_ThenReturnZero() {
        List<Integer> integers = new ArrayList<>();

        int actualSum = integers
            .stream()
            .mapToInt(i -> i)
            .sum();

        int expectedSum = 0;

        assertEquals(expectedSum, actualSum);
    }

    @Test
    public void givenListOfIntegers_WhenPredicateForAnyMatchOnlyForSingleElementReturnTrue_ThenReturnTrue() {
        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 10; i++) integers.add(i);

        assertTrue(integers.stream().anyMatch(i -> i == 9));
    }

    @Test
    public void givenListOfIntegers_WhenPredicateForAnyMatchAlwaysReturnFalse_ThenReturnFalse() {
        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 10; i++) integers.add(i);

        assertFalse(integers.stream().anyMatch(i -> i >= 10));
    }
}
