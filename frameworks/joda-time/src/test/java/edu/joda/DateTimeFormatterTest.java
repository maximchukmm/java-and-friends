package edu.joda;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import java.util.Locale;

import static edu.joda.util.JodaUtils.dateTime;
import static org.junit.Assert.assertEquals;

public class DateTimeFormatterTest {

    @Test
    public void print_Pattern_dd_MMMM() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd MMMM");
        DateTime dateTime = dateTime("2018-08-14 10:15:00");

        String actualFormattedDate = formatter.print(dateTime);

        assertEquals("14 August", actualFormattedDate);
    }

    @Test
    public void print_Pattern_dd_MMMM_WithRussianLocale() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd MMMM").withLocale(Locale.forLanguageTag("ru-RU"));
        DateTime dateTime = dateTime("2018-08-14 10:15:00");

        String actualFormattedDate = formatter.print(dateTime);

        assertEquals("14 августа", actualFormattedDate);
    }

    @Test
    public void print_Pattern_dd_MM() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.MM");
        DateTime dateTime = dateTime("2018-08-14 10:15:00");

        String actualFormattedDate = formatter.print(dateTime);

        assertEquals("14.08", actualFormattedDate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseDateTime_WhenSpareCharacters_ThenThrowIllegalArgumentException() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

        formatter.parseDateTime("2018-08-15 10:00:00.0");
    }
}
