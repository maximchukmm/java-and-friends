package edu.joda;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static edu.joda.util.JodaUtils.dateTime;
import static edu.joda.util.JodaUtils.interval;
import static org.junit.Assert.*;

public class IntervalTest {

    @Test
    public void intervalsSubtractionIntervalsNotOverlapped() {
        Interval minuend = interval(
            "2018-10-10 10:00:00",
            "2018-10-10 12:00:00"
        );
        Interval subtrahend = interval(
            "2018-10-10 13:00:00",
            "2018-10-10 15:00:00"
        );

        List<Interval> expected = new ArrayList<>();
        expected.add(minuend);

        List<Interval> actual = IntervalDemo.intervalsSubtraction(minuend, subtrahend);

        assertEquals(expected.size(), actual.size());
        assertTrue(expected.containsAll(actual));
    }

    @Test
    public void intervalsSubtractionIntervalsOverlappedAndNotContainsEachOther() {
        Interval minuend = interval(
            "2018-10-10 10:00:00",
            "2018-10-10 12:00:00"
        );
        Interval subtrahend = interval(
            "2018-10-10 11:00:00",
            "2018-10-10 15:00:00"
        );

        List<Interval> expected = new ArrayList<>();
        expected.add(interval(
            "2018-10-10 10:00:00",
            "2018-10-10 11:00:00")
        );

        List<Interval> actual = IntervalDemo.intervalsSubtraction(minuend, subtrahend);

        assertEquals(expected.size(), actual.size());
        assertTrue(actual.containsAll(expected));
    }

    @Test
    public void intervalsSubtractionIntervalsOverlappedAndMinuendContainsSubtrahend() {
        Interval minuend = interval(
            "2018-10-10 10:00:00",
            "2018-10-10 12:00:00"
        );
        Interval subtrahend = interval(
            "2018-10-10 10:30:00",
            "2018-10-10 11:30:00"
        );

        List<Interval> expected = new ArrayList<>();
        expected.add(interval(
            "2018-10-10 10:00:00",
            "2018-10-10 10:30:00")
        );
        expected.add(interval(
            "2018-10-10 11:30:00",
            "2018-10-10 12:00:00"
        ));

        List<Interval> actual = IntervalDemo.intervalsSubtraction(minuend, subtrahend);

        assertEquals(expected.size(), actual.size());
        assertTrue(actual.containsAll(expected));
    }

    @Test
    public void intervalsSubtractionIntervalsOverlappedAndSubtrahendContainsMinuend() {
        Interval minuend = interval(
            "2018-10-10 10:00:00",
            "2018-10-10 12:00:00"
        );
        Interval subtrahend = interval(
            "2018-10-10 09:00:00",
            "2018-10-10 13:00:00"
        );

        List<Interval> actual = IntervalDemo.intervalsSubtraction(minuend, subtrahend);

        assertTrue(actual.isEmpty());
    }

    @Test
    public void contains_WhenIntervalCreatedWithStartAndEndDateTime_ThenReturnTrueBecauseStartDateTimeIsIncludedInInterval() {
        DateTime start = dateTime("2018-10-10 10:00:00");
        DateTime end = dateTime("2018-10-10 12:00:00");

        Interval interval = new Interval(start, end);

        assertTrue(interval.contains(start));
    }

    @Test
    public void contains_WhenIntervalCreatedWithStartAndEndDateTime_ThenReturnFalseBecauseEndDateTimeIsExcludedInInterval() {
        DateTime start = dateTime("2018-10-10 10:00:00");
        DateTime end = dateTime("2018-10-10 12:00:00");

        Interval interval = new Interval(start, end);

        assertFalse(interval.contains(end));
    }

    @Test
    public void abuts() {
        DateTime start = dateTime("2018-10-10 10:00:00");
        DateTime end = dateTime("2018-10-10 12:00:00");
        Interval interval = new Interval(start, end);

        Interval notOverlapCompletelyBefore = interval(
            "2018-10-10 08:00:00",
            "2018-10-10 09:59:00"
        );
        assertFalse(interval.abuts(notOverlapCompletelyBefore));

        Interval abutToStart = new Interval(start.minusMinutes(1), start);
        assertTrue(interval.abuts(abutToStart));
        assertTrue(abutToStart.abuts(interval));

        Interval overlapWithStart = interval(
            "2018-10-10 09:00:00",
            "2018-10-10 10:01:00"
        );
        assertFalse(interval.abuts(overlapWithStart));

        Interval zeroDurationStart = new Interval(start, start);
        assertTrue(interval.abuts(zeroDurationStart));
        assertTrue(zeroDurationStart.abuts(interval));

        Interval zeroDurationEnd = new Interval(end, end);
        assertTrue(interval.abuts(zeroDurationEnd));
        assertTrue(zeroDurationEnd.abuts(interval));

        Interval abutToEnd = new Interval(end, end.plusMinutes(1));
        assertTrue(interval.abuts(abutToEnd));
        assertTrue(abutToEnd.abuts(interval));

        Interval notOverlapCompletelyAfter = interval(
            "2018-10-10 12:01:00",
            "2018-10-10 13:00:00"
        );
        assertFalse(interval.abuts(notOverlapCompletelyAfter));
    }

    @Test
    public void whenCreateIntervalWithEqualStartAndEnd_ThenDoesntThrowException() {
        interval(
            "2018-10-10 10:00:00",
            "2018-10-10 10:00:00"
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreateIntervalWithEndBeforeStart_ThenThrowIllegalArgumentException() {
        interval(
            "2018-10-10 01:00:00",
            "2018-10-10 00:00:00"
        );
    }

    @Test
    public void isAfter_WhenDateTimeIsBeforeOfIntervalStart_ThenReturnTrue() {
        DateTime dateTime = dateTime("2018-06-23 15:00:00");
        Interval interval = interval(
            "2018-06-23 15:00:01",
            "2018-06-23 16:00:01"
        );

        assertTrue(interval.isAfter(dateTime));
    }

    @Test
    public void isAfter_WhenDateTimeIsEqualToIntervalStart_ThenReturnFalse() {
        DateTime dateTime = dateTime("2018-06-23 15:00:00");
        Interval interval = interval(
            "2018-06-23 15:00:00",
            "2018-06-23 16:00:00"
        );

        assertFalse(interval.isAfter(dateTime));
    }

    @Test
    public void isAfter_WhenDateTimeInInterval_ThenReturnFalse() {
        DateTime dateTime = dateTime("2018-06-23 15:30:00");
        Interval interval = interval(
            "2018-06-23 15:00:00",
            "2018-06-23 16:00:00"
        );

        assertFalse(interval.isAfter(dateTime));
    }

    @Test
    public void isAfter_WhenDateTimeIsEqualToIntervalEnd_ThenReturnFalse() {
        DateTime dateTime = dateTime("2018-06-23 16:00:00");
        Interval interval = interval(
            "2018-06-23 15:00:00",
            "2018-06-23 16:00:00"
        );

        assertFalse(interval.isAfter(dateTime));
    }

    @Test
    public void isAfter_WhenDateTimeIsAfterOfIntervalEnd_ThenReturnFalse() {
        DateTime dateTime = dateTime("2018-06-23 16:00:01");
        Interval interval = interval(
            "2018-06-23 15:00:00",
            "2018-06-23 16:00:00"
        );

        assertFalse(interval.isAfter(dateTime));
    }

    @Test
    public void sortIntervalsByStart_WhenIntervalsSortedByStart_ThenPreviousStartIsBeforeOrEqualToNextStart() {
        List<Interval> intervals = new ArrayList<>();
        intervals.add(interval(
            "2018-06-23 22:00:00",
            "2018-06-24 06:00:00")
        );
        intervals.add(interval(
            "2018-06-23 10:00:00",
            "2018-06-23 12:00:00")
        );
        intervals.add(interval(
            "2018-06-23 15:00:00",
            "2018-06-23 22:00:00")
        );

        intervals.sort(Comparator.comparing(Interval::getStart));

        assertTrue(isIntervalsSortedByStart(intervals));
    }

    private boolean isIntervalsSortedByStart(List<Interval> intervals) {
        for (int i = 0; i < intervals.size() - 1; i++) {
            DateTime currentStart = intervals.get(i).getStart();
            DateTime nextStart = intervals.get(i + 1).getStart();
            if (currentStart.compareTo(nextStart) >= 1)
                return false;
        }
        return true;
    }
}
