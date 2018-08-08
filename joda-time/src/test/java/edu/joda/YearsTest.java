package edu.joda;

import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.junit.Assert;
import org.junit.Test;

public class YearsTest {
    @Test
    public void yearsBetween_WhenFirstDateBeforeSecondDate_ThenReturnPositiveInteger() {
        LocalDate firstDate = new LocalDate(2013, 1, 1);
        LocalDate secondDate = new LocalDate(2018, 1, 1);

        int expectedYearsBetweenDates = 5;

        int actualYearsBetweenDates = Years.yearsBetween(firstDate, secondDate).getYears();

        Assert.assertEquals(expectedYearsBetweenDates, actualYearsBetweenDates);
    }

    @Test
    public void yearsBetween_WhenFirstDateAfterSecondDate_ThenReturnNegativeInteger() {
        LocalDate firstDate = new LocalDate(2018, 1, 1);
        LocalDate secondDate = new LocalDate(2013, 1, 1);

        int expectedYearsBetweenDates = -5;

        int actualYearsBetweenDates = Years.yearsBetween(firstDate, secondDate).getYears();

        Assert.assertEquals(expectedYearsBetweenDates, actualYearsBetweenDates);
    }

    @Test
    public void yearsBetween_WhenFirstDateEqualsSecondDate_ThenReturnZeroInteger() {
        LocalDate firstDate = new LocalDate(2018, 1, 1);
        LocalDate secondDate = new LocalDate(2018, 1, 1);

        int expectedYearsBetweenDates = 0;

        int actualYearsBetweenDates = Years.yearsBetween(firstDate, secondDate).getYears();

        Assert.assertEquals(expectedYearsBetweenDates, actualYearsBetweenDates);
    }
}
