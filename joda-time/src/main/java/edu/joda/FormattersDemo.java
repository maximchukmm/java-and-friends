package edu.joda;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Locale;
import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class FormattersDemo {
    private static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("dd.MM");
    private static final DateTimeZone ZONE = DateTimeZone.forID("Europe/Moscow");

    public static void main(String[] args) {
//        List<DateTime> dates = new ArrayList<>();
//        for (int i = 0; i < 1_000_000; i++) {
//            dates.add(DateTime.now(DateTimeZone.UTC).plusDays(i));
//        }
//
//        long start1 = System.currentTimeMillis();
//        listWithStream(dates);
//        long end1 = System.currentTimeMillis();
//
//        long start2 = System.currentTimeMillis();
//        treeSet(dates);
//        long end2 = System.currentTimeMillis();
//
//        long start3 = System.currentTimeMillis();
//        withProcessing(dates);
//        long end3 = System.currentTimeMillis();
//
//        System.out.println("First  : " + (end1 - start1));
//        System.out.println("Second : " + (end2 - start2));
//        System.out.println("Third  : " + (end3 - start3));

//        DateTime dateTime = new DateTime(2018, 8, 23, 21, 0, 0, 0, DateTimeZone.UTC);
//        System.out.println(FORMATTER.print(dateTime));
//        System.out.println(FORMATTER.print(dateTime.withZone(ZONE)));

//        NavigableSet<DateTime> dates = new TreeSet<>();
//        for (int i = 0; i < 10; i++ ){
//            dates.add(DateTime.now().plusMinutes(i).withMillisOfSecond(0).withSecondOfMinute(0));
//        }
//        System.out.println(dates);
//        List<String> stringDates = dates
//            .stream()
//            .map(DateTime::toString)
//            .collect(Collectors.toList());
//        System.out.println(stringDates);

//        DateTimeFormatter dayFormatter = DateTimeFormat.forPattern("dd MMMM").withLocale(Locale.forLanguageTag("ru-RU"));
//        DateTime dateTime = new DateTime(2018, 8, 20, 21, 0, 0, 0, DateTimeZone.UTC);
//
//        System.out.println(dayFormatter.print(dateTime));
//        System.out.println(dayFormatter.print(dateTime.withZone(DateTimeZone.forID("Europe/Moscow"))));
//
//        System.out.println();
//
//        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
//        System.out.println(dateTimeFormatter.print(dateTime));
//        System.out.println(dateTimeFormatter.print(dateTime.withZone(DateTimeZone.forID("Europe/Moscow"))));
//
//        System.out.println();
//
//        System.out.println(dateTime.toString());
//        System.out.println(dateTime.toString(dateTimeFormatter));
//
//        System.out.println();
//
//        System.out.println(dateTime.withZone(DateTimeZone.forID("Europe/Moscow")).toString());
//        System.out.println(dateTime.withZone(DateTimeZone.forID("Europe/Moscow")).toString(dateTimeFormatter));

    }

    private static void treeSet(List<DateTime> dates) {
        NavigableSet<String> set = new TreeSet<>();
        dates.forEach(date -> set.add(FORMATTER.print(date.withZone(ZONE))));
        // System.out.println(set.size());
    }

    private static void listWithStream(List<DateTime> dates) {
        List<String> formattedDates = dates
            .stream()
            .map(date -> FORMATTER.print(date.withZone(ZONE)))
            .distinct()
            .sorted()
            .collect(Collectors.toList());
        // System.out.println(formattedDates.size());
    }
}
