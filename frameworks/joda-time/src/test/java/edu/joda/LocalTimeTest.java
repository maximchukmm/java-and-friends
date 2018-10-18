package edu.joda;

import org.joda.time.LocalTime;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

//todo test - create LocalTime instance with DateTimeZone
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
}
