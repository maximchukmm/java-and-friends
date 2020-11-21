package edu.joda;

import lombok.Data;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.TimeZone;

public class DateTimeZoneDemo {
    public static void main(String[] args) {
//        List<DateTimeZoneInfo> zones = DateTimeZone.getAvailableIDs()
//            .stream()
//            .map(DateTimeZoneInfo::new)
//            .sorted()
//            .collect(Collectors.toList());
//
//        zones.forEach(System.out::println);

        System.out.println(TimeZone.getTimeZone("UTC"));
    }
}

@Data
class DateTimeZoneInfo implements Comparable<DateTimeZoneInfo> {
    private static final DateTime NOW = DateTime.now(DateTimeZone.UTC);

    private String id;
    private int millisOffset;

    DateTimeZoneInfo(String id) {
        this(DateTimeZone.forID(id));
    }

    DateTimeZoneInfo(DateTimeZone zone) {
        this.id = zone.getID();
        this.millisOffset = zone.getOffset(NOW);
    }

    @Override
    public String toString() {
        return String.format("%40s %5d", id, millisOffset / (1000 * 60 * 60));
    }

    @Override
    public int compareTo(DateTimeZoneInfo o) {
        return id.compareTo(o.id) == 0 ? 0 : Integer.compare(millisOffset, o.millisOffset);
    }
}
