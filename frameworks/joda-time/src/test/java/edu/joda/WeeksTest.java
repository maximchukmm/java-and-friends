package edu.joda;

import org.joda.time.DateTime;
import org.joda.time.Weeks;
import org.junit.Test;

import static edu.joda.util.JodaUtils.EUROPE_MOSCOW;
import static edu.joda.util.JodaUtils.dateTime;
import static org.junit.Assert.assertEquals;

public class WeeksTest {

    @Test
    public void weeksBetween_WhenFirstMondayInOneYearAndSecondMondayInAnotherYear_ThenReturnCorrectNumberOfWeeksBetweenMondays() {
        DateTime firstMonday = dateTime("2018-12-10 00:00:00", EUROPE_MOSCOW);
        DateTime secondMonday = dateTime("2019-01-14 00:00:00", EUROPE_MOSCOW);

        assertEquals(5, Weeks.weeksBetween(firstMonday, secondMonday).getWeeks());
    }
}
