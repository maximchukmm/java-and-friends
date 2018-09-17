package edu.joda;

import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;


class DateTimeDemo {
    private static final DateTimeZone UTC = DateTimeZone.UTC;
    private static final DateTimeZone EUROPE_MOSCOW = DateTimeZone.forID("Europe/Moscow");

    public static void main(String[] args) {
//        DateTimeZone moscowZone = DateTimeZone.forID("Europe/Moscow");
//        DateTime dateTime = new DateTime(2018, 5, 29, 13, 0, 0, 0, DateTimeZone.UTC);
//        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
//
//        System.out.println("without format with UTC: " + dateTime);
//        System.out.println("   with format with UTC: " + dateTime.toString(formatter));
//        System.out.println("with format with MOSCOW: " + dateTime.withZone(moscowZone).toString(formatter));

//        DateTime now = DateTime.now(DateTimeZone.UTC);
//
//        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd MMMM");
//        DateTimeFormatter ruFormatter = DateTimeFormat.forPattern("dd MMMM").withLocale(Locale.forLanguageTag("ru-RU"));
//        DateTimeFormatter enFormatter = DateTimeFormat.forPattern("dd MMMM").withLocale(Locale.forLanguageTag("en-US"));
//        DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
//
//        System.out.println(formatter.print(now));
//        System.out.println(ruFormatter.print(now));
//        System.out.println(enFormatter.print(now));
//        System.out.println(dateFormatter.print(now));

//        DateTime dateTime = new DateTime(2018, 10, 20, 15, 59, 48, 123, DateTimeZone.UTC);
//        System.out.println(dateTime);
//        int minuteOfHour = dateTime.getMinuteOfHour();
//        int minute = (int) Math.round(1.0 * minuteOfHour / 5) * 5;
//        System.out.println(dateTime.withSecondOfMinute(0).withMillisOfSecond(0).plusMinutes(minute - minuteOfHour));

//        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withZone(DateTimeZone.UTC);
//        DateTime dateTime = formatter.parseDateTime("2018-01-15 01:00:00");
//        System.out.println(dateTime);

//        DateTime romeBeforeDST = new DateTime(2018, 3, 25, 1, 55, 0, 0, EUROPE_ROME);
//        System.out.println(EUROPE_ROME);
//        System.out.println(romeBeforeDST + " - utc - " + romeBeforeDST.withZone(UTC));
//        System.out.println(romeBeforeDST.plusHours(24) + " - utc - " + romeBeforeDST.plusHours(24).withZone(UTC));
//        System.out.println(romeBeforeDST.plusDays(1) + " - utc - " + romeBeforeDST.plusDays(1).withZone(UTC));
//        System.out.println(UTC);
//        System.out.println(new DateTime(2018, 3, 25, 1, 55, 0, 0, UTC));
//        System.out.println(new DateTime(2018, 3, 25, 1, 55, 0, 0, UTC).plusHours(24));
//        System.out.println(new DateTime(2018, 3, 25, 1, 55, 0, 0, UTC).plusDays(1));

//        DateTime romeBeforeDSTInUtcTimeZone = new DateTime(2018, 3, 25, 1, 55, 0, 0, UTC);
//        DateTime romeInDSTInUtcTimeZone = new DateTime(2018, 3, 25, 2, 5, 0, 0, UTC);
//        DateTime romeAfterDSTInUtcTimeZone = new DateTime(2018, 3, 25, 3, 5, 0, 0, UTC);
//
//        System.out.println(romeBeforeDSTInUtcTimeZone.withZone(EUROPE_ROME));
//        System.out.println(romeInDSTInUtcTimeZone.withZone(EUROPE_ROME));
//        System.out.println(romeAfterDSTInUtcTimeZone.withZone(EUROPE_ROME));
//
//        System.out.println();
//
//        System.out.println(romeBeforeDSTInUtcTimeZone.withZoneRetainFields(EUROPE_ROME));
//        System.out.println(romeInDSTInUtcTimeZone.withZoneRetainFields(EUROPE_ROME));
//        System.out.println(romeAfterDSTInUtcTimeZone.withZoneRetainFields(EUROPE_ROME));

//        System.out.println(DateTime.now());
//        System.out.println(DateTime.now().getZone().getOffset(DateTime.now()) / 1000 / 60);
//        System.out.println(DateTime.now().getZone());
//        System.out.println(DateTime.parse("2018-08-22T16:30:01Z"));
//        System.out.println(DateTime.parse("2018-08-22T16:30:01.123Z"));
//        System.out.println(DateTime.parse("2018-08-22T16:30:01.123+03:00"));
//        System.out.println(DateTime.parse("2018-08-22T16:30.5"));
//        System.out.println(DateTime.parse("2018-08-22T"));

//        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
//
//        DateTimeZone moscow = DateTimeZone.forID("Europe/Moscow");
//        DateTimeZone samara = DateTimeZone.forID("Europe/Samara");
//
//        DateTime utc = new DateTime(2018, 8, 23, 21, 0, 0, 0, DateTimeZone.UTC);
//        DateTime d2 = utc.withZone(moscow);
//        DateTime d3 = utc.withZone(samara);
//
//        System.out.println("date time");
//
//        System.out.println(formatter.print(utc));
//        System.out.println(formatter.withZone(moscow).print(d2));
//        System.out.println(formatter.withZone(moscow).print(utc));
//        System.out.println(formatter.print(d2));
//
//        System.out.println();
//
//        System.out.println(formatter.print(utc));
//        System.out.println(formatter.withZone(samara).print(d3));
//        System.out.println(formatter.withZone(samara).print(utc));
//        System.out.println(formatter.print(d3));
//
//        System.out.println();
//        System.out.println();
//
//        System.out.println("local date time");
//
//        System.out.println(formatter.print(utc.toLocalDateTime()));
//        System.out.println(formatter.withZone(moscow).print(d2.toLocalDateTime()));
//        System.out.println(formatter.withZone(moscow).print(utc.toLocalDateTime()));
//        System.out.println(formatter.print(d2.toLocalDateTime()));
//
//        System.out.println();
//
//
//        System.out.println(formatter.print(utc.toLocalDateTime()));
//        System.out.println(formatter.withZone(samara).print(d3.toLocalDateTime()));
//        System.out.println(formatter.withZone(samara).print(utc.toLocalDateTime()));
//        System.out.println(formatter.print(d3.toLocalDateTime()));
//
//        System.out.println();

//        DateTimeZone moscow = DateTimeZone.forID("Europe/Moscow");
//        DateTimeZone samara = DateTimeZone.forID("Europe/Samara");
//        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.of(2018, 8, 23, 16, 15, 0));
//
//        System.out.println(timestamp);
//
//        System.out.println();
//
//        System.out.println(new DateTime(timestamp));
//        System.out.println(new DateTime(timestamp).withZone(moscow));
//        System.out.println(new DateTime(timestamp).withZone(samara));
//
//        System.out.println();
//
//        System.out.println(timestamp.toLocalDateTime());
//        System.out.println(new DateTime(timestamp.toLocalDateTime().toString()));
//        System.out.println(new DateTime(timestamp.toLocalDateTime().toString()).withZone(moscow));
//        System.out.println(new DateTime(timestamp.toLocalDateTime().toString()).withZone(samara));
//
//        System.out.println();
//
//        System.out.println(timestamp.toInstant());

//        String value = "2018-08-23";
//        System.out.println("value = " + value);
//
//        LocalDate localDate = LocalDate.parse(value);
//        System.out.println("local date = " + localDate);
//
//        DateTimeZone moscow = DateTimeZone.forID("Europe/Moscow");
//        DateTimeZone samara = DateTimeZone.forID("Europe/Samara");
//
//        DateTime dateTimeMoscow = localDate.toDateTimeAtStartOfDay(moscow);
//        System.out.println("date time moscow = " + dateTimeMoscow);
//
//        DateTime dateTimeSamara = localDate.toDateTimeAtStartOfDay(samara);
//        System.out.println("date time samara = " + dateTimeSamara);
//
//        System.out.println("date time moscow millis = " + dateTimeMoscow.getMillis());
//        System.out.println("date time samara millis = " + dateTimeSamara.getMillis());
//
//        Date dateMoscow = new Date(dateTimeMoscow.getMillis());
//        System.out.println("date moscow = " + dateMoscow);
//        Date dateSamara = new Date(dateTimeSamara.getMillis());
//        System.out.println("date samara = " + dateSamara);
//
//        System.out.println();
    }
}
