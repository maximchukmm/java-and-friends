package edu.joda;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;


class DateTimeDemo {
    private static final DateTimeZone UTC = DateTimeZone.UTC;
    private static final DateTimeZone EUROPE_MOSCOW = DateTimeZone.forID("Europe/Moscow");

    public static DateTime roundToFiveMinutes(DateTime dateTime) {
        int mod = dateTime.getMinuteOfHour() % 5;
        switch (mod) {
            case 1:
            case 2:
                return dateTime.minusMinutes(mod).withSecondOfMinute(0).withMillisOfSecond(0);
            case 3:
            case 4:
                return dateTime.plusMinutes(5 - mod).withSecondOfMinute(0).withMillisOfSecond(0);
            default:
                return dateTime.withSecondOfMinute(0).withMillisOfSecond(0);
        }
    }

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
    }
}
