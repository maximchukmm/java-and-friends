package edu.joda;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Test;

//todo extract tests to corresponding (DateTimeTest and DateTimeZoneTest) classes
public class JodaDemoTest {
    private static final DateTimeZone UTC = DateTimeZone.UTC;
    private static final DateTimeZone EUROPE_MOSCOW = DateTimeZone.forID("Europe/Moscow");              // +3 hours
    private static final DateTimeZone ANTARCTICA_ROTHERA = DateTimeZone.forID("Antarctica/Rothera");    // -3 hours

    @Test
    public void compareDateTimesWithDifferentZones() {
        DateTime utc = new DateTime(2018, 10, 10, 15, 0, 0, 0, UTC);
        DateTime moscow = new DateTime(2018, 10, 10, 15, 0, 0, 0, EUROPE_MOSCOW);

        Assert.assertNotEquals(utc, moscow);
        Assert.assertTrue(utc.compareTo(moscow) >= 1);
        Assert.assertTrue(moscow.compareTo(utc) <= -1);

        DateTime utcPlusMoscowZone = new DateTime(2018, 10, 10, 12, 0, 0, 0, UTC);
        Assert.assertNotEquals(moscow, utcPlusMoscowZone);
        Assert.assertTrue(moscow.isEqual(utcPlusMoscowZone));
    }

    @Test
    public void setTheSameZoneToDateTime() {
        DateTime expected = new DateTime(2018, 10, 10, 15, 0, 0, 0, EUROPE_MOSCOW);
        DateTime actual = expected.withZone(EUROPE_MOSCOW);

        Assert.assertEquals(expected, actual);
        Assert.assertTrue(expected.isEqual(actual));
    }

    @Test
    public void checkDateTimeZoneOffset() {
        int moscowOffsetInMilliseconds = EUROPE_MOSCOW.getOffset(null);
        int rotheraOffsetInMilliseconds = ANTARCTICA_ROTHERA.getOffset(null);

        int moscowOffsetInHours = +3;
        int rotheraOffsetInHours = -3;

        Assert.assertEquals(moscowOffsetInHours, millisecondsToHours(moscowOffsetInMilliseconds));
        Assert.assertEquals(rotheraOffsetInHours, millisecondsToHours(rotheraOffsetInMilliseconds));
    }

    private int millisecondsToHours(int milliseconds) {
        return milliseconds / (1000 * 60 * 60);
    }
}
