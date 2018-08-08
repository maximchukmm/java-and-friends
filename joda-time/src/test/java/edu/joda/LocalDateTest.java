package edu.joda;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;

import static edu.joda.util.JodaUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(Enclosed.class)
public class LocalDateTest {

    @RunWith(Parameterized.class)
    public static class HoursInMonthsParameterizedTest {

        @Parameters(name = "{index}: {1} hours in {0}")
        public static Iterable<Object[]> parameters() {
            return Arrays.asList(new Object[][]{
                //leap year
                {new LocalDate(2016, 1, 1), 24 * 31},
                {new LocalDate(2016, 2, 1), 24 * 29},
                {new LocalDate(2016, 3, 1), 24 * 31},
                {new LocalDate(2016, 4, 1), 24 * 30},
                {new LocalDate(2016, 5, 1), 24 * 31},
                {new LocalDate(2016, 6, 1), 24 * 30},
                {new LocalDate(2016, 7, 1), 24 * 31},
                {new LocalDate(2016, 8, 1), 24 * 31},
                {new LocalDate(2016, 9, 1), 24 * 30},
                {new LocalDate(2016, 10, 1), 24 * 31},
                {new LocalDate(2016, 11, 1), 24 * 30},
                {new LocalDate(2016, 12, 1), 24 * 31},
                //non-leap year
                {new LocalDate(2017, 1, 1), 24 * 31},
                {new LocalDate(2017, 2, 1), 24 * 28},
                {new LocalDate(2017, 3, 1), 24 * 31},
                {new LocalDate(2017, 4, 1), 24 * 30},
                {new LocalDate(2017, 5, 1), 24 * 31},
                {new LocalDate(2017, 6, 1), 24 * 30},
                {new LocalDate(2017, 7, 1), 24 * 31},
                {new LocalDate(2017, 8, 1), 24 * 31},
                {new LocalDate(2017, 9, 1), 24 * 30},
                {new LocalDate(2017, 10, 1), 24 * 31},
                {new LocalDate(2017, 11, 1), 24 * 30},
                {new LocalDate(2017, 12, 1), 24 * 31},
            });
        }

        private LocalDate date;
        private int expectedHoursInMonth;

        public HoursInMonthsParameterizedTest(LocalDate date, int expectedHoursInMonth) {
            this.date = date;
            this.expectedHoursInMonth = expectedHoursInMonth;
        }

        @Test
        public void getHoursOfMonth_ForLeapOrNonLeapYear_ReturnExpectedHoursInAMonth() {
            assertEquals(expectedHoursInMonth, date.dayOfMonth().getMaximumValue() * 24);
        }
    }

    public static class OtherLocalDateTests {
        @Test
        public void getMonthOfYear_WhenLocalDateWithSeptemberMonth_ThenReturnInteger9() {
            int september = 9;
            LocalDate localDate = new LocalDate(2017, september, 1);

            assertEquals(september, localDate.getMonthOfYear());
        }

        @Test
        public void dayOfMonth_WithMinimumValue_ReturnFirstDayOfMonth() {
            LocalDate localDate = new LocalDate(2017, 10, 14);
            LocalDate expectedFirstDayOfMonth = new LocalDate(2017, 10, 1);

            LocalDate actualFirstDayOfMonth = localDate.dayOfMonth().withMinimumValue();

            Assert.assertEquals(expectedFirstDayOfMonth, actualFirstDayOfMonth);
        }

        @Test
        public void dayOfMonth_WithMaximumValue_ReturnLastDayOfMonth() {
            LocalDate localDate = new LocalDate(2017, 10, 14);
            LocalDate expectedFirstDayOfMonth = new LocalDate(2017, 10, 31);

            LocalDate actualLastDayOfMonth = localDate.dayOfMonth().withMaximumValue();

            Assert.assertEquals(expectedFirstDayOfMonth, actualLastDayOfMonth);
        }

        @Test
        public void toDateTimeAtStartOfDay_WhenGettingStartOfDayWithTimeZoneWithoutDST_ThenReturnMidnightLocalTime() {
            LocalDate localDate = localDate("2018-03-25");
            DateTime startOfDay = localDate.toDateTimeAtStartOfDay(EUROPE_MOSCOW);

            LocalTime actual = startOfDay.toLocalTime();

            assertEquals(LocalTime.MIDNIGHT, actual);
        }

        @Test
        public void toDateTimeAtStartOfDay_WhenGettingStartOfDayWithDifferentTimeZones_ThenGettingDifferentMomentsOfTime() {
            LocalDate localDate = localDate("2018-03-25");

            DateTime startOfDayWithUtcTimeZone = localDate.toDateTimeAtStartOfDay(UTC);
            DateTime startOfDayWithMoscowTimeZone = localDate.toDateTimeAtStartOfDay(EUROPE_MOSCOW);

            assertFalse(startOfDayWithMoscowTimeZone.isEqual(startOfDayWithUtcTimeZone));
        }
    }
}
