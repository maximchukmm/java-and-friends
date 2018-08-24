package edu.time;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class JavaTimeDemo {
    public static void main(String[] args) {
//        try {
//            System.out.println(Instant.parse("2018-08-23"));
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        System.out.println(LocalDate.parse("2018-08-23"));
//        System.out.println(LocalDate.parse("2018-08-23").atStartOfDay(ZoneId.of("Europe/Samara")));

        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.of(2018, 8, 23, 16, 15, 0));
        System.out.println(timestamp);
    }
}
