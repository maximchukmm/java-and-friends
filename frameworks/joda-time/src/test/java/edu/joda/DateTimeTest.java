package edu.joda;

import edu.joda.util.JodaUtils;
import org.jfree.data.time.Week;
import org.joda.time.DateTime;
import org.joda.time.IllegalInstantException;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Test;

import static edu.joda.util.JodaUtils.*;
import static org.junit.Assert.*;

public class DateTimeTest {
    @Test
    public void toLocalDate() {
        DateTime dateTime = dateTime("2017-05-11 18:12:33");
        LocalDate expected = localDate("2017-05-11");

        LocalDate actual = dateTime.toLocalDate();

        assertEquals(expected, actual);
    }

    @Test
    public void toLocalTime() {
        DateTime dateTime = dateTime("2017-05-11 18:12:33");
        LocalTime expected = localTime("18:12:33");

        LocalTime actual = dateTime.toLocalTime();

        assertEquals(expected, actual);
    }

    @Test
    public void withSecondOfMinute() {
        DateTime dateTime = dateTimeWithMillis("2018-12-20 15:15:59.999");
        DateTime expected = dateTimeWithMillis("2018-12-20 15:15:00.999");

        DateTime actual = dateTime.withSecondOfMinute(0);

        assertEquals(expected, actual);
    }

    @Test
    public void withMillisOfSecond() {
        DateTime dateTime = dateTimeWithMillis("2018-12-20 15:15:59.999");
        DateTime expected = dateTimeWithMillis("2018-12-20 15:15:59.000");

        DateTime actual = dateTime.withMillisOfSecond(0);

        assertEquals(expected, actual);
    }

    @Test
    public void getMillis_WhenSameMomentOfTimeWithSameTimeZones_ThenReturnSameMillis() {
        DateTime utc1 = dateTimeWithMillis("2018-12-20 15:30:00.000");
        DateTime utc2 = dateTimeWithMillis("2018-12-20 15:30:00.000");

        assertEquals(utc1.getMillis(), utc2.getMillis());
    }

    @Test
    public void getMillis_WhenSameMomentOfTimeWithDifferentTimeZones_ThenReturnSameMillis() {
        DateTime utc = dateTimeWithMillis("2018-12-20 12:30:00.000");
        DateTime moscow = dateTimeWithMillis("2018-12-20 15:30:00.000", EUROPE_MOSCOW);

        assertEquals(utc.getMillis(), moscow.getMillis());
    }

    @Test
    public void equals_WhenSameMomentOfTimeAndTimeZones_ThenReturnTrue() {
        DateTime utc1 = dateTime("2018-12-20 15:30:00", UTC);
        DateTime utc2 = dateTime("2018-12-20 15:30:00", UTC);

        assertEquals(utc1, utc2);
    }

    @Test
    public void equals_WhenSameMomentOfTimeAndDifferentTimeZones_ThenReturnFalse() {
        DateTime utc = dateTime("2018-12-20 12:30:00", UTC);
        DateTime moscow = dateTime("2018-12-20 15:30:00", EUROPE_MOSCOW);

        assertNotEquals(utc, moscow);
    }

    @Test
    public void isEqual_WhenSameMomentOfTimeAndTimeZones_ThenReturnTrue() {
        DateTime utc1 = dateTime("2018-12-20 15:30:00", UTC);
        DateTime utc2 = dateTime("2018-12-20 15:30:00", UTC);

        assertTrue(utc1.isEqual(utc2));
    }

    @Test
    public void isEqual_WhenSameMomentOfTimeAndDifferentTimeZones_ThenReturnTrue() {
        DateTime utc = dateTime("2018-12-20 12:30:00", UTC);
        DateTime moscow = dateTime("2018-12-20 15:30:00", EUROPE_MOSCOW);

        assertTrue(utc.isEqual(moscow));
    }

    @Test
    public void isBefore_WhenSameMomentOfTimeAndSameTimeZones_ThenReturnFalse() {
        DateTime dateTime = dateTime("2018-11-20 12:00:00");
        DateTime moscow1 = dateTime.withZone(EUROPE_MOSCOW);
        DateTime moscow2 = dateTime.withZone(EUROPE_MOSCOW);

        assertFalse(moscow1.isBefore(moscow2));
        assertFalse(moscow2.isBefore(moscow1));
    }

    @Test
    public void isBefore_WhenSameMomentOfTimeAndDifferentTimeZones_ThenReturnFalse() {
        DateTime dateTime = dateTime("2018-11-20 12:00:00");
        DateTime moscow = dateTime.withZone(EUROPE_MOSCOW);
        DateTime rome = dateTime.withZone(EUROPE_ROME);

        assertFalse(moscow.isBefore(rome));
        assertFalse(rome.isBefore(moscow));
    }

    @Test
    public void timeZone_WhenBeforeDayLightSavingTime_ThenOffsetIsEqualToStandardOffsetForCountryThatObserveDST() {
        // Летнее время в Италии с 25 марта 02:00 по 28 октября 03:00. В это время разница с UTC +2 часа.
        // В другое же время разница с UTC +1 час
        DateTime romeBeforeDST = dateTime("2018-03-25 01:55:00", EUROPE_ROME);

        long romeBeforeDSTMillis = romeBeforeDST.getMillis();

        assertEquals(hoursToMillis(1), EUROPE_ROME.getOffset(romeBeforeDSTMillis));
        assertEquals(EUROPE_ROME.getOffset(romeBeforeDSTMillis), EUROPE_ROME.getStandardOffset(romeBeforeDSTMillis));
    }

    @Test
    public void timeZone_WhenAfterDayLightSavingTime_ThenOffsetIsntEqualToStandardOffsetForCountryThatObserveDST() {
        // Летнее время в Италии с 25 марта 02:00 по 28 октября 03:00. В это время разница с UTC +2 часа.
        // В другое же время разница с UTC +1 час
        DateTime romeAfterDST = dateTime("2018-03-25 03:00:00", EUROPE_ROME);

        long romeDSTMillis = romeAfterDST.getMillis();

        assertEquals(hoursToMillis(2), EUROPE_ROME.getOffset(romeDSTMillis));
        assertNotEquals(EUROPE_ROME.getOffset(romeDSTMillis), EUROPE_ROME.getStandardOffset(romeDSTMillis));
    }

    @Test(expected = IllegalInstantException.class)
    public void timeZone_WhenInDayLightSavingTime_ThenThrowException() {
        DateTime romeInDST = dateTime("2018-03-25 02:30:00", EUROPE_ROME);
    }

    @Test
    public void getDayOfMonth_WhenSameMomentsOfTimeWithDifferentTimeZonesWithMidnightIntersection_ThenReturnDifferentDayOfMonth() {
        DateTime dateTimeInUTC = dateTime("2018-08-20 22:00:00");
        DateTime dateTimeInMoscow = dateTime("2018-08-21 01:00:00", EUROPE_MOSCOW);

        assertTrue(dateTimeInUTC.isEqual(dateTimeInMoscow));
        assertNotEquals(dateTimeInUTC.getDayOfMonth(), dateTimeInMoscow.getDayOfMonth());
        assertEquals(20, dateTimeInUTC.getDayOfMonth());
        assertEquals(21, dateTimeInMoscow.getDayOfMonth());
    }

    @Test
    public void withZone_WhenGetNewInstanceWithNewTimeZone_ThenGetSameMomentOfTime() {
        DateTime dateTimeInUTC = dateTime("2018-07-30 20:00:00");
        DateTime dateTimeInMoscow = dateTimeInUTC.withZone(EUROPE_MOSCOW);

        assertTrue(dateTimeInUTC.isEqual(dateTimeInMoscow));
        assertEquals(dateTimeInUTC.getMillis(), dateTimeInMoscow.getMillis());
    }

    @Test
    public void withZone_WhenGenNewInstanceWithNewTimeZone_ThenGetDifferentHoursOfDay() {
        DateTime dateTimeInUTC = dateTime("2018-07-30 20:00:00");
        DateTime dateTimeInMoscow = dateTimeInUTC.withZone(EUROPE_MOSCOW);

        assertNotEquals(dateTimeInUTC.getHourOfDay(), dateTimeInMoscow.getHourOfDay());
    }

    @Test
    public void withZoneRetainFields_WhenGetNewInstanceWithNewTimeZone_ThenGetDifferentMomentOfTime() {
        DateTime dateTimeInUTC = dateTime("2018-07-30 20:00:00");
        DateTime dateTimeInMoscow = dateTimeInUTC.withZoneRetainFields(EUROPE_MOSCOW);

        assertFalse(dateTimeInUTC.isEqual(dateTimeInMoscow));
        assertNotEquals(dateTimeInUTC.getMillis(), dateTimeInMoscow.getMillis());
    }

    @Test
    public void withZoneRetainFields_WhenGetNewInstanceWithNewTimeZone_ThenGetSameHoursOfDay() {
        DateTime dateTimeInUTC = dateTime("2018-07-30 20:00:00");
        DateTime dateTimeInMoscow = dateTimeInUTC.withZoneRetainFields(EUROPE_MOSCOW);

        assertEquals(dateTimeInUTC.getHourOfDay(), dateTimeInMoscow.getHourOfDay());
    }

    @Test
    public void withTimeAtStartOfDay() {
        DateTime dateTime = dateTime("2018-08-01 15:30:00");

        DateTime actual = dateTime.withTimeAtStartOfDay();
        DateTime expected = dateTime("2018-08-01 00:00:00");

        assertTrue(expected.isEqual(actual));
        assertEquals(expected, actual);
    }

    @Test
    public void withTimeAtStartOfDay_WhenCallOnMidnight_ThenReturnThatMidnight() {
        DateTime dateTime = dateTime("2018-08-01 00:00:00");

        DateTime actual = dateTime.withTimeAtStartOfDay();
        DateTime expected = dateTime("2018-08-01 00:00:00");

        assertTrue(expected.isEqual(actual));
        assertEquals(expected, actual);
    }

    @Test
    public void withTimeAtStartOfDay_WhenCallOnDateTimeWithTimeZone_ThenReturnMidnightForThatTimeZone() {
        DateTime dateTime = dateTime("2018-08-02 15:00:00", EUROPE_MOSCOW);

        DateTime actual = dateTime.withTimeAtStartOfDay();
        DateTime expected = dateTime("2018-08-02 00:00:00", EUROPE_MOSCOW);

        assertTrue(expected.isEqual(actual));
        assertEquals(expected, actual);
    }

    @Test
    public void getWeekOfWeekYear_WhenBeginningOfWeekAndSameMomentOfTimeWithDifferentZones_ThenReturnDifferentNumbersOfWeek() {
        DateTime utc = dateTime("2018-10-21 21:00:00");
        DateTime moscow = dateTime("2018-10-22 00:00:00", EUROPE_MOSCOW);

        assertTrue(utc.isEqual(moscow));
        assertTrue(utc.getWeekOfWeekyear() != moscow.getWeekOfWeekyear());
    }

    //todo complete test for converting org.jfree.data.time.Week to DateTime
    @Test
    public void When_Then1() {
        Week week = new Week(1, 2017);

        System.out.println(week.getStart());
        System.out.println(week.getEnd());

        System.out.println();

        System.out.println(new DateTime(week.getFirstMillisecond(), UTC));
        System.out.println(new DateTime(week.getLastMillisecond(), UTC));

        System.out.println();

        System.out.println(new DateTime(week.getFirstMillisecond()));
        System.out.println(new DateTime(week.getLastMillisecond()));

        System.out.println();

        System.out.println(new DateTime(week.getFirstMillisecond()).withZoneRetainFields(UTC));
        System.out.println(new DateTime(week.getFirstMillisecond()).withZoneRetainFields(UTC).withZone(EUROPE_MOSCOW));
        System.out.println(new DateTime(week.getLastMillisecond()));
    }
}
