package edu.joda.util;

import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

abstract public class JodaUtils {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_TIME_WITH_MILLIS_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");

    private static final DateTimeFormatter LOCAL_DATE_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");
    private static final DateTimeFormatter LOCAL_TIME_UTC_FORMATTER = DateTimeFormat.forPattern("HH:mm:ss").withZone(DateTimeZone.UTC);

    public static final DateTimeZone UTC = DateTimeZone.UTC;
    public static final DateTimeZone EUROPE_MOSCOW = DateTimeZone.forID("Europe/Moscow");
    public static final DateTimeZone EUROPE_ROME = DateTimeZone.forID("Europe/Rome");

    public static LocalTime localTime(String localTime) {
        return LOCAL_TIME_UTC_FORMATTER.parseLocalTime(localTime);
    }

    public static LocalDate localDate(String localDate) {
        return LOCAL_DATE_FORMATTER.parseLocalDate(localDate);
    }

    public static DateTime dateTime(String dateTime) {
        return dateTime(dateTime, DateTimeZone.UTC);
    }

    public static DateTime dateTime(String dateTime, DateTimeZone zone) {
        return DATE_TIME_FORMATTER.withZone(zone).parseDateTime(dateTime);
    }

    public static DateTime dateTimeWithMillis(String dateTimeWithMillis, DateTimeZone zone) {
        return DATE_TIME_WITH_MILLIS_FORMATTER.withZone(zone).parseDateTime(dateTimeWithMillis);
    }

    public static DateTime dateTimeWithMillis(String dateTimeWithMillis) {
        return dateTimeWithMillis(dateTimeWithMillis, DateTimeZone.UTC);
    }

    public static Interval interval(String start, String end, DateTimeZone zone) {
        return new Interval(
            DATE_TIME_FORMATTER.withZone(zone).parseDateTime(start),
            DATE_TIME_FORMATTER.withZone(zone).parseDateTime(end)
        );
    }

    public static Interval interval(String start, String end) {
        return interval(start, end, DateTimeZone.UTC);
    }

    public static int hoursToMillis(int hours) {
        assert hours > 0 && hours < 25;
        return hours * 60 * 60 * 1000;
    }
}
