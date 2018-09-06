package edu.collections;

import org.junit.Test;

import java.util.*;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class CollectionTest {

    @Test
    public void forEach_WhenIterateThroughEmptyCollection_ThenDoesNotDoAnyIteration() {
        int i = 0;
        List<String> emptyList = new ArrayList<>();

        for (String each : emptyList)
            i++;

        assertEquals(0, i);
    }

    @Test(expected = ConcurrentModificationException.class)
    public void removeInLoop_WhenRemoveFirstElementInLoopFromArrayListWithOneElementWithoutUsingIterator_ThenThrowConcurrentModificationException() {
        Integer removingFirstElement = 1;
        List<Integer> list = new ArrayList<>();
        list.add(removingFirstElement);

        for (Integer element : list) {
            if (element.equals(removingFirstElement))
                list.remove(element);
        }
    }

    @Test
    public void removeInLoop_WhenRemoveFirstElementInLoopFromArrayListWithTwoElementsWithoutUsingIterator_ThenThrowNothingButShouldTo() {
        Integer removingFirstElement = 1;
        List<Integer> list = new ArrayList<>();
        list.add(removingFirstElement);
        list.add(2);

        for (Integer element : list) {
            if (element.equals(removingFirstElement))
                list.remove(element);
        }
    }

    @Test
    public void removeInLoop_WhenRemoveFirstElementInLoopFromArrayListWithOneElementUsingIterator_ThenRemoveElement() {
        Integer removingFirstElement = 1;
        List<Integer> list = new ArrayList<>();
        list.add(removingFirstElement);

        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            Integer element = iterator.next();
            if (element.equals(removingFirstElement)) {
                iterator.remove();
            }
        }

        assertTrue(list.isEmpty());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void whenRemoveFromListCreatedWith_Arrays_asList_ThenThrowException() {
        Integer removingElement = 1;
        List<Integer> list = Arrays.asList(removingElement, 2);

        list.remove(removingElement);
    }

    @Test
    public void removeIf_WhenRemoveFromEmptyCollection_ThenReturnFalse() {
        List<Integer> list = new ArrayList<>();

        assertFalse(list.removeIf(i -> i >= 5));
    }

    @Test
    public void removeIf_WhenNonEmptyCollectionDoesNotContainElementsThatSatisfyTheGivenPredicate_ThenDoNotRemoveElementsAndReturnFalse() {
        List<Integer> list = new ArrayList<>();
        IntStream.range(6, 10).forEach(list::add);

        assertFalse(list.removeIf(i -> i <= 5));

        List<Integer> expected = new ArrayList<>();
        IntStream.range(6, 10).forEach(expected::add);

        assertTrue(expected.containsAll(list));
    }

    @Test
    public void removeIf_WhenCollectionContainsElementsThatSatisfyTheGivenPredicate_ThenRemoveThatElementsAndReturnTrue() {
        List<Integer> list = new ArrayList<>();
        IntStream.range(1, 10).forEach(list::add);

        assertTrue(list.removeIf(i -> i <= 5));

        List<Integer> expected = new ArrayList<>();
        IntStream.range(6, 10).forEach(expected::add);

        assertTrue(expected.containsAll(list));
    }
}
