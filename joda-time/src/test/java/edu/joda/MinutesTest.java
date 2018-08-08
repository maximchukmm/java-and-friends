package edu.joda;

import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.junit.Test;

import static edu.joda.util.JodaUtils.dateTime;
import static org.junit.Assert.assertEquals;

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
}
