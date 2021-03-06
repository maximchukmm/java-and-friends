package edu.collections;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ListIteratorTest {
    private int listSize = 5;
    private List<Integer> list;

    @Test
    public void add_WhenAddElement_ThenAddBeforeElementReturnedByLastNext() {
        ListIterator<Integer> listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            Integer integer = listIterator.next();
            if (integer == 3) listIterator.add(777);
        }

        MatcherAssert.assertThat(list, IsCollectionWithSize.hasSize(listSize + 1));
        MatcherAssert.assertThat(list, Matchers.contains(0, 1, 2, 3, 777, 4));
    }

    @Test
    public void remove_WhenRemoveElement_ThenRemoveElementThatReturnedByLastNext() {
        ListIterator<Integer> listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            Integer integer = listIterator.next();
            if (integer == 3) listIterator.remove();
        }

        MatcherAssert.assertThat(list, IsCollectionWithSize.hasSize(listSize - 1));
        MatcherAssert.assertThat(list, Matchers.contains(0, 1, 2, 4));
        Assert.assertTrue(list.get(3).equals(4));
    }

    @Test
    public void add_WhenAddElementBeforeCallNext_ThenAddElementAtTheBeginningOfTheList() {
        ListIterator<Integer> listIterator = list.listIterator();

        listIterator.add(777);

        MatcherAssert.assertThat(list, IsCollectionWithSize.hasSize(listSize + 1));
        MatcherAssert.assertThat(list, Matchers.contains(777, 0, 1, 2, 3, 4));
    }

    @Test
    public void add_WhenCallPreviousAfterAddNewElement_ThenReturnThatAddedElement() {
        ListIterator<Integer> listIterator = list.listIterator();

        listIterator.next();
        listIterator.add(777);
        listIterator.previous();

        Assert.assertEquals(list.get(1), listIterator.next());
    }

    @Before
    public void setUp() {
        list = new ArrayList<>();
        for (int i = 0; i < listSize; i++) list.add(i);
    }
}
