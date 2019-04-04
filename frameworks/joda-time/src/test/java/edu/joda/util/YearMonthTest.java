package edu.joda.util;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.YearMonth;
import org.junit.Test;

import static edu.joda.util.JodaUtils.UTC;

public class YearMonthTest {

    //todo complete YearMonth to DateTime test
    @Test
    public void When_Then1() {
        LocalDate date = new LocalDate(2019, 3, 10);
        DateTime dateTime = date.toDateTimeAtStartOfDay(UTC);
        YearMonth yearMonth = new YearMonth(dateTime);

        System.out.println(yearMonth);
        System.out.println(yearMonth.toDateTime(null));
        System.out.println(yearMonth.toLocalDate(1).toDateTimeAtStartOfDay(UTC));
    }
}
