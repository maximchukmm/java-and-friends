package edu.joda;

import org.jfree.data.time.Week;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.Weeks;
import org.joda.time.YearMonth;

class LocalDateDemo {
    public static void main(String[] args) {
        LocalDate localDate = new LocalDate(2018, 8, 10);

        System.out.println(localDate.toDateTimeAtStartOfDay());
        System.out.println(localDate.toDateTimeAtMidnight());
        System.out.println(localDate.toDateTimeAtStartOfDay(DateTimeZone.UTC));
        System.out.println(localDate.toDateTimeAtMidnight(DateTimeZone.UTC));
        System.out.println(localDate.toDateTimeAtStartOfDay(DateTimeZone.forID("Europe/Moscow")));
        System.out.println(localDate.toDateTimeAtMidnight(DateTimeZone.forID("Europe/Moscow")));

        System.out.println();

        YearMonth yearMonth = new YearMonth(localDate);
        System.out.println(yearMonth);

        System.out.println();

        Weeks week = Weeks.weeks(localDate.getWeekyear());
        System.out.println(week);

        System.out.println();

        System.out.println(localDate.getWeekOfWeekyear());
        System.out.println(localDate.getWeekyear());
        System.out.println(new Week(localDate.getWeekOfWeekyear(), localDate.getWeekyear()));

        System.out.println();

        for (int i = 0; i < 7; i++) {
            System.out.println(localDate.plusDays(i) + " - day of week - " + localDate.plusDays(i).getDayOfWeek());
        }
    }
}
