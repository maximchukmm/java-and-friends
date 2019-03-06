package edu.joda;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static edu.joda.util.JodaUtils.*;
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

        List<Interval> actual = IntervalUtils.intervalsSubtraction(minuend, subtrahend);

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

        List<Interval> actual = IntervalUtils.intervalsSubtraction(minuend, subtrahend);

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

        List<Interval> actual = IntervalUtils.intervalsSubtraction(minuend, subtrahend);

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

        List<Interval> actual = IntervalUtils.intervalsSubtraction(minuend, subtrahend);

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
    public void overlaps_WhenIntervalsEquals_ThenReturnTrue() {
        Interval i1 = interval("2018-10-01 10:00:00", "2018-10-01 12:00:00");
        Interval i2 = interval("2018-10-01 10:00:00", "2018-10-01 12:00:00");

        assertTrue(i1.overlaps(i2));
    }

    @Test
    public void overlaps_WhenStartAndEndOfOneIntervalIsEqualToStartOfOtherInterval_ThenReturnFalse() {
        Interval i1 = interval("2018-10-01 10:00:00", "2018-10-01 12:00:00");
        Interval i2 = interval("2018-10-01 10:00:00", "2018-10-01 10:00:00");

        assertFalse(i1.overlaps(i2));
    }

    @Test
    public void overlaps_WhenStartAndEndOfOneIntervalIsEqualToEndOfOtherInterval_ThenReturnFalse() {
        Interval i1 = interval("2018-10-01 10:00:00", "2018-10-01 12:00:00");
        Interval i2 = interval("2018-10-01 12:00:00", "2018-10-01 12:00:00");

        assertFalse(i1.overlaps(i2));
    }

    @Test
    public void overlaps_WhenStartAndEndOfOneIntervalIsEqualToTheMiddleOfOtherInterval_ThenReturnTrue() {
        Interval i1 = interval("2018-10-01 10:00:00", "2018-10-01 12:00:00");
        Interval i2 = interval("2018-10-01 11:00:00", "2018-10-01 11:00:00");

        assertTrue(i1.overlaps(i2));
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
    public void whenCreateIntervalWithStartInOneTimeZoneAndEndInAnotherTimeZone_ThenUseStartTimeZoneForStartAndEnd() {
        DateTime startWithNonUtc = dateTime("2019-02-01 10:00:00", DateTimeZone.forID("Asia/Omsk"));
        DateTime endWithUtc = dateTime("2019-02-01 17:15:00");

        Interval intervalWithNonUtcStart = new Interval(startWithNonUtc, endWithUtc);

        assertTrue(startWithNonUtc.isEqual(intervalWithNonUtcStart.getStart()));
        assertTrue(endWithUtc.isEqual(intervalWithNonUtcStart.getEnd()));
        assertEquals(startWithNonUtc, intervalWithNonUtcStart.getStart());
        assertNotEquals(endWithUtc, intervalWithNonUtcStart.getEnd());


        DateTime startWithUtc = dateTime("2019-02-01 10:00:00");
        DateTime endWithNonUtc = dateTime("2019-02-01 17:15:00", DateTimeZone.forID("Asia/Omsk"));

        Interval intervalWithUtcStart = new Interval(startWithUtc, endWithNonUtc);

        assertTrue(startWithUtc.isEqual(intervalWithUtcStart.getStart()));
        assertTrue(endWithNonUtc.isEqual(intervalWithUtcStart.getEnd()));
        assertEquals(startWithUtc, intervalWithUtcStart.getStart());
        assertNotEquals(endWithNonUtc, intervalWithUtcStart.getEnd());
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

        assertTrue(IntervalUtils.isIntervalsSortedByStart(intervals));
    }

    @Test
    public void isEqual_WhenStartAndEndOfIntervalsEqual_ThenReturnTrue() {
        Interval interval1 = interval("2018-08-20 15:00:00", "2018-08-20 16:00:00");
        Interval interval2 = interval("2018-08-20 15:00:00", "2018-08-20 16:00:00");

        assertTrue(interval1.isEqual(interval2));
    }

    @Test
    public void isEqual_WhenStartOfIntervalsEqualAndEndNotEqual_ThenReturnFalse() {
        Interval interval1 = interval("2018-08-20 15:00:00", "2018-08-20 16:00:00");
        Interval interval2 = interval("2018-08-20 15:00:00", "2018-08-20 17:00:00");

        assertFalse(interval1.isEqual(interval2));
    }

    @Test
    public void isEqual_WhenStartOfIntervalsNotEqualAndEndEqual_ThenReturnFalse() {
        Interval interval1 = interval("2018-08-20 14:00:00", "2018-08-20 16:00:00");
        Interval interval2 = interval("2018-08-20 15:00:00", "2018-08-20 16:00:00");

        assertFalse(interval1.isEqual(interval2));
    }

    @Test
    public void isEqual_WhenStartAndEndOfIntervalsNotEqual_ThenReturnFalse() {
        Interval interval1 = interval("2018-08-20 15:00:00", "2018-08-20 16:00:00");
        Interval interval2 = interval("2018-08-20 15:20:00", "2018-08-20 15:40:00");

        assertFalse(interval1.isEqual(interval2));
    }

    @Test
    public void getDurationInSeconds_WhenDurationOfIntervalInSecondsDoesNotSuitToInteger_ThenDoNotThrowException() {
        Interval interval = interval("1970-01-01 00:00:00", "2070-01-01 00:00:00");

        IntervalUtils.getDurationInSeconds(interval);
    }

    @Test
    public void getDurationInSeconds_WhenDurationOfIntervalInSecondsDoesNotSuitToInteger_ThenReturnSecondsGreaterMaxValueOfInteger() {
        Interval interval = interval("1970-01-01 00:00:00", "2070-01-01 00:00:00");

        long actualSeconds = IntervalUtils.getDurationInSeconds(interval);

        assertTrue(actualSeconds > Integer.MAX_VALUE);
    }

    @Test
    public void dayOfWeek_WithMinimumValue() {
        DateTime monday = dateTime("2019-03-04 00:00:00", EUROPE_MOSCOW);
        DateTime tuesday = dateTime("2019-03-05 00:00:00", EUROPE_MOSCOW);
        DateTime sunday = dateTime("2019-03-10 00:00:00", EUROPE_MOSCOW);

        assertEquals(monday.dayOfWeek().withMinimumValue(), monday);
        assertNotEquals(tuesday.dayOfWeek().withMinimumValue(), tuesday);
        assertEquals(tuesday.dayOfWeek().withMinimumValue(), monday);
        assertEquals(sunday.dayOfWeek().withMinimumValue().plusDays(6), sunday);
    }
}
