package edu.time;

import org.junit.Test;

import java.util.Arrays;
import java.util.TimeZone;

public class TimeZoneTest {

    @Test
    //todom rename method
    public void test001() {
        Arrays.stream(TimeZone.getAvailableIDs())
            .map(TimeZone::getTimeZone)
            .forEach(System.out::println);
    }
}
