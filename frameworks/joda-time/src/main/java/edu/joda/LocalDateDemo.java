package edu.joda;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

class LocalDateDemo {
    public static void main(String[] args) {
//        LocalDate localDate = new LocalDate(2018, 8, 10);
//
//        System.out.println(localDate.toDateTimeAtStartOfDay());
//        System.out.println(localDate.toDateTimeAtMidnight());
//        System.out.println(localDate.toDateTimeAtStartOfDay(DateTimeZone.UTC));
//        System.out.println(localDate.toDateTimeAtMidnight(DateTimeZone.UTC));
//        System.out.println(localDate.toDateTimeAtStartOfDay(DateTimeZone.forID("Europe/Moscow")));
//        System.out.println(localDate.toDateTimeAtMidnight(DateTimeZone.forID("Europe/Moscow")));
//
//        System.out.println();
//
//        YearMonth yearMonth = new YearMonth(localDate);
//        System.out.println(yearMonth);
//
//        System.out.println();
//
//        Weeks week = Weeks.weeks(localDate.getWeekyear());
//        System.out.println(week);
//
//        System.out.println();
//
//        System.out.println(localDate.getWeekOfWeekyear());
//        System.out.println(localDate.getWeekyear());
//        System.out.println(new Week(localDate.getWeekOfWeekyear(), localDate.getWeekyear()));
//
//        System.out.println();
//
//        for (int i = 0; i < 7; i++) {
//            System.out.println(localDate.plusDays(i) + " - day of week - " + localDate.plusDays(i).getDayOfWeek());
//        }

//        for (int i = 0; i < 50; i++)
//            System.out.println(LocalDate.now().plusDays(i).getDayOfWeek());

        System.out.println(getListDaysByPeriod(new LocalDate(2018, 9, 1), new LocalDate(2018, 9, 9  )));
    }

    public static List<LocalDate> getListDaysByPeriod(org.joda.time.LocalDate from, org.joda.time.LocalDate to) {
        int numOfDays = Days.daysBetween(from, to).getDays() + 1;
        return Stream.iterate(from, date -> date.plusDays(1))
            .limit(numOfDays)
            .collect(toList());
    }
}
