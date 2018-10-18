package edu.joda;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.Seconds;
import org.junit.Test;

import static edu.joda.util.JodaUtils.localTime;
import static org.junit.Assert.assertEquals;

public class SecondsTest {
    @Test
    public void secondsBetween_WhenFirstDateTimeIsBeforeSecondDateTime_ThenReturnPositiveSeconds() {
        DateTime firstDate = new DateTime(2017, 5, 11, 7, 47, 11);
        DateTime secondDate = new DateTime(2017, 5, 11, 7, 48, 13);

        int actualSecondsBetween = Seconds.secondsBetween(firstDate, secondDate).getSeconds();
        int expectedSecondsBetween = 62;

        assertEquals(expectedSecondsBetween, actualSecondsBetween);
    }

    @Test
    public void secondsBetween_WhenFirstDateTimeIsBeforeSecondDateTime_ThenReturnNegativeSeconds() {
        DateTime firstDate = new DateTime(2017, 5, 11, 7, 48, 13);
        DateTime secondDate = new DateTime(2017, 5, 11, 7, 47, 11);

        int actualSecondsBetween = Seconds.secondsBetween(firstDate, secondDate).getSeconds();
        int expectedSecondsBetween = -62;

        assertEquals(expectedSecondsBetween, actualSecondsBetween);
    }

    @Test
    public void secondsBetween_WhenFirstLocalTimeIsBeforeSecondLocalTime_ThenReturn() {
        LocalTime firstLocalTime = localTime("15:00:00");
        LocalTime secondLocalTime = localTime("15:00:30");

        int actualSecondsBetween = Seconds.secondsBetween(firstLocalTime, secondLocalTime).getSeconds();
        int expectedSecondsBetween = 30;

        assertEquals(expectedSecondsBetween, actualSecondsBetween);
    }

    @Test
    public void secondsBetween_WhenFirstLocalTimeIsEqualToSecondLocalTime_ThenReturnPositiveSeconds() {
        LocalTime firstLocalTime = localTime("15:00:00");
        LocalTime secondLocalTime = localTime("15:00:00");

        int actualSecondsBetween = Seconds.secondsBetween(firstLocalTime, secondLocalTime).getSeconds();
        int expectedSecondsBetween = 0;

        assertEquals(expectedSecondsBetween, actualSecondsBetween);
    }

    @Test
    public void secondsBetween_WhenFirstLocalTimeIsAfterSecondLocalTime_ThenReturnNegativeSeconds() {
        LocalTime firstLocalTime = localTime("15:00:30");
        LocalTime secondLocalTime = localTime("15:00:00");

        int actualSecondsBetween = Seconds.secondsBetween(firstLocalTime, secondLocalTime).getSeconds();
        int expectedSecondsBetween = -30;

        assertEquals(expectedSecondsBetween, actualSecondsBetween);
    }

    @Test
    public void secondsBetween_WhenCalledWithLocalTime_ThenReturnDifferenceBetweenMilliseconds() {
        LocalTime firstLocalTime = localTime("23:00:00");
        LocalTime secondLocalTime = localTime("01:00:00");

        int actualSecondsBetween = Seconds.secondsBetween(firstLocalTime, secondLocalTime).getSeconds();
        int expectedSecondsBetween = (secondLocalTime.getMillisOfDay() - firstLocalTime.getMillisOfDay()) / 1000;

        assertEquals(expectedSecondsBetween, actualSecondsBetween);
    }
}
