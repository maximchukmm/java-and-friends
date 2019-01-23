package edu.joda;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.Minutes;
import org.junit.Test;

import static edu.joda.util.JodaUtils.dateTime;
import static edu.joda.util.JodaUtils.localTime;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MinutesTest {
    @Test
    public void minutesBetween_WhenFirstInstantIsBeforeSecondInstant_ThenReturnPositiveValue() {
        DateTime from = dateTime("2018-08-20 10:00:00");
        DateTime to = dateTime("2018-08-20 10:01:00");

        int expectedMinutesBetween = 1;
        int actualMinutesBetween = Minutes.minutesBetween(from, to).getMinutes();

        assertEquals(expectedMinutesBetween, actualMinutesBetween);
    }

    @Test
    public void minutesBetween_WhenFirstInstantIsEqualToSecondInstant_ThenReturnZeroValue() {
        DateTime from = dateTime("2018-08-20 10:00:00");
        DateTime to = dateTime("2018-08-20 10:00:00");

        int expectedMinutesBetween = 0;
        int actualMinutesBetween = Minutes.minutesBetween(from, to).getMinutes();

        assertEquals(expectedMinutesBetween, actualMinutesBetween);
    }

    @Test
    public void minutesBetween_WhenFirstInstantIsAfterSecondInstant_ThenReturnNegativeValue() {
        DateTime from = dateTime("2018-08-20 10:01:00");
        DateTime to = dateTime("2018-08-20 10:00:00");

        int expectedMinutesBetween = -1;
        int actualMinutesBetween = Minutes.minutesBetween(from, to).getMinutes();

        assertEquals(expectedMinutesBetween, actualMinutesBetween);
    }

    @Test
    public void minutesBetween_WhenFirstLocalTimeBeforeSecondLocalTime_ThenReturnPositiveInteger() {
        LocalTime start = localTime("10:00:00");
        LocalTime end = localTime("11:00:00");

        int expectedMinutesBetween = 60;
        int actualMinutesBetween = Minutes.minutesBetween(start, end).getMinutes();

        assertTrue(actualMinutesBetween > 0);
        assertEquals(expectedMinutesBetween, actualMinutesBetween);
    }

    @Test
    public void minutesBetween_WhenFirstLocalTimeAfterSecondLocalTime_ThenReturnNegativeInteger() {
        LocalTime start = localTime("11:00:00");
        LocalTime end = localTime("10:00:00");

        int expectedMinutesBetween = -60;
        int actualMinutesBetween = Minutes.minutesBetween(start, end).getMinutes();

        assertTrue(actualMinutesBetween < 0);
        assertEquals(expectedMinutesBetween, actualMinutesBetween);
    }

    @Test
    public void minutesBetween_IsEqualToDifferenceBetweenMillisOfEndAndStartLocalTimes() {
        LocalTime start = localTime("23:00:00");
        LocalTime end = localTime("01:00:00");

        int expectedMinutesBetweenCalculated = (end.getMillisOfDay() - start.getMillisOfDay()) / (1000 * 60);
        int expectedMinutesBetween = -1320;
        int actualMinutesBetween = Minutes.minutesBetween(start, end).getMinutes();

        assertEquals(expectedMinutesBetween, expectedMinutesBetweenCalculated);
        assertEquals(expectedMinutesBetween, actualMinutesBetween);
    }
}
