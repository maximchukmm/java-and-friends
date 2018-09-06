package edu.time;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TimestampTest {

    @Test
    public void valueOf_WhenParseStringWithCorrectMask_ThenCreateTimestamp() {
        String dateTime = "2018-09-23 01:01:01";

        Timestamp expected = Timestamp.valueOf(LocalDateTime.of(2018, 9, 23, 1, 1, 1));
        Timestamp actual = Timestamp.valueOf(dateTime);

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void valueOf_WhenParseStringWithWrongMask_ThenThrowIllegalArgumentException() {
        Timestamp.valueOf("2018-09-23");
    }
}
