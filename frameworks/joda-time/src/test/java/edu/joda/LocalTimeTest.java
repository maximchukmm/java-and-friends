package edu.joda;

import org.joda.time.LocalTime;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static edu.joda.util.JodaUtils.EUROPE_MOSCOW;
import static edu.joda.util.JodaUtils.dateTime;


public class LocalTimeTest {
    @Test
    public void isBefore_WhenSearchForMinimumLocalTimeInListWithMidnight_ThenReturnMidnight() {
        List<LocalTime> localTimes = new ArrayList<>();
        LocalTime currentLocalTime = LocalTime.MIDNIGHT;
        int stepInMinutes = 10;
        localTimes.add(currentLocalTime);
        for (int i = stepInMinutes; i < 24 * 60; i += stepInMinutes) {
            localTimes.add(currentLocalTime.plusMinutes(i));
        }

        LocalTime expectedMinimumLocalTime = LocalTime.MIDNIGHT;

        LocalTime actualMinimumLocalTime = new LocalTime(12, 0, 0);
        for (LocalTime localTime : localTimes) {
            if (localTime.isBefore(actualMinimumLocalTime)) actualMinimumLocalTime = localTime;
        }

        Assert.assertEquals(expectedMinimumLocalTime, actualMinimumLocalTime);
    }

    @Test
    public void newLocalTime_WhenCreateFromMillisAndZone_ThenReturnLocalTimeShiftedToZone() {
        LocalTime actualLocalTime = new LocalTime(dateTime("2018-09-15 10:00:00"), EUROPE_MOSCOW);
        LocalTime expectedLocalTime = new LocalTime(13, 0, 0);

        Assert.assertEquals(expectedLocalTime, actualLocalTime);
    }
}
