package edu.joda;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class JodaDemo {
    private static final DateTimeZone UTC = DateTimeZone.UTC;
    private static final DateTimeZone EUROPE_MOSCOW = DateTimeZone.forID("Europe/Moscow");

    private static List<Interval> getOverlapsOfIntervalsWith(List<Interval> intervals, LocalTime start, LocalTime end) {
        List<Interval> intersections = new ArrayList<>();

        for (Interval interval : intervals) {
            Interval anotherInterval = new Interval(interval.getStart().withTime(start), interval.getStart().withTime(end));
            if (interval.overlaps(anotherInterval))
                intersections.add(interval.overlap(anotherInterval));
        }

        return intersections;
    }

    private static List<Interval> splitIntervalsByMidnight(List<Interval> intervals) {
        List<Interval> splitIntervals = new ArrayList<>();

        intervals.sort(Comparator.comparing(Interval::getStart));

        DateTime currentMidnight = intervals.get(0).getStart().withMillisOfDay(0);
        for (Interval interval : intervals) {
            while (currentMidnight.isBefore(interval.getStart()))
                currentMidnight = currentMidnight.plusDays(1);

            if (!interval.contains(currentMidnight)) {
                splitIntervals.add(interval);
                continue;
            }

            System.out.println("Split interval " + interval + " to:");
            System.out.println("1: " + interval.withEnd(currentMidnight));
            System.out.println("2: " + interval.withStart(currentMidnight));

            System.out.println();

            splitIntervals.add(interval.withEnd(currentMidnight));
            splitIntervals.add(interval.withStart(currentMidnight));
        }

        return splitIntervals;
    }


    private static List<Interval> splitOnDayAndNightIntervals(Interval workInterval) {
        List<Interval> intervals = new ArrayList<>();

        DateTime workStart = workInterval.getStart();
        DateTime workEnd = workInterval.getEnd();

        DateTime nightStart = workStart
            .withHourOfDay(22)
            .withMinuteOfHour(0)
            .withMillisOfSecond(0);
        DateTime nightEnd = nightStart.plusHours(8);

        Interval nightInterval = new Interval(nightStart, nightEnd);

        if (nightInterval.contains(workInterval)) {
            intervals.add(workInterval);
        } else if (isDateTimeBetween(workStart, nightStart, nightEnd)) {
            intervals.add(new Interval(workStart, nightEnd));
            intervals.add(new Interval(nightEnd, workEnd));
        } else if (isDateTimeBetween(workEnd, nightStart, nightEnd)) {
            intervals.add(new Interval(workStart, nightStart));
            intervals.add(new Interval(nightStart, workEnd));
        }

        return intervals;
    }

    private static boolean isDateTimeBetween(DateTime dateTime, DateTime start, DateTime end) {
        return dateTime.isAfter(start) && dateTime.isBefore(end);
    }

    private static void intervalsDemo(Interval workInterval) {
        System.out.println("==============Interval demonstration start===============");
        DateTime nightStart = workInterval.getStart()
            .withHourOfDay(22)
            .withMinuteOfHour(0)
            .withMillisOfSecond(0);
        DateTime nightEnd = nightStart.plusHours(8);
        Interval nightInterval = new Interval(nightStart, nightEnd);

        System.out.println("workInterval: " + workInterval);
        System.out.println("nightInterval: " + nightInterval);

        System.out.println();

        System.out.println("workInterval.overlap(nightInterval): " + workInterval.overlap(nightInterval));
        System.out.println("nightInterval.overlap(workInterval): " + nightInterval.overlap(workInterval));

        System.out.println();

        System.out.println("workInterval.gap(nightInterval): " + workInterval.gap(nightInterval));
        System.out.println("nightInterval.gap(workInterval): " + nightInterval.gap(workInterval));

        System.out.println();

        System.out.println("workInterval.overlaps(nightInterval): " + workInterval.overlaps(nightInterval));
        System.out.println("nightInterval.overlaps(workInterval): " + nightInterval.overlaps(workInterval));

        System.out.println("==============Interval demonstration end===============");

        System.out.println();
    }

    private static boolean isIntersectNightWorkTime(Interval workInterval) {
        LocalTime startNight = new LocalTime(22, 0, 0);
        LocalTime endNight = new LocalTime(6, 0, 0);

        LocalTime start = workInterval.getStart().toLocalTime();
        LocalTime end = workInterval.getEnd().toLocalTime();

        System.out.println(String.format("Start is after endNight: %s is after %s: %b", start, endNight, start.isAfter(endNight)));
        System.out.println(String.format("End is before startNight: %s is before %s: %b", end, startNight, end.isBefore(startNight)));

        return !(start.isAfter(endNight) && end.isBefore(startNight));
    }

    public static void main(String[] args) throws Exception {
//        DateTime workStart = new DateTime(2018, 3, 16, 23, 0, 0, 0);
//        DateTime workEnd = workStart.plusHours(2);
//        Interval workInterval = new Interval(workStart, workEnd);
//
//        intervalsDemo(workInterval);
//
//        System.out.println("workInterval intersects night work time: " + isIntersectNightWorkTime(workInterval));
//
//        System.out.println();
//
//        List<Interval> split = splitOnDayAndNightIntervals(workInterval);
//        System.out.println("Split intervals:");
//        for (Interval interval : split) {
//            System.out.println(String.format("interval: %s, length: %d", interval, interval.toDuration().getStandardMinutes()));
//        }
//
//        System.out.println();

//        DateTimeZone zone = DateTimeZone.forOffsetHours(-15);
//        DateTime workStart = new DateTime(
//            2018, 3, 1,
//            10, 0, 0, 0)
//            .withZone(zone);
//        LocalDateTime workStartLocal = workStart.toLocalDateTime();
//        System.out.println("workStart: " + workStart);
//        System.out.println("workStart UTC: " + workStart.withZone(DateTimeZone.UTC));
//        System.out.println("workStartLocal: " + workStartLocal);
//        DateTime newWorkStart = workStartLocal.toDateTime(zone);
//        System.out.println("newWorkStart: " + newWorkStart);
//        System.out.println("newWorkStart UTC: " + newWorkStart.withZone(DateTimeZone.UTC));

//        LocalDate beginOfMonth = new LocalDate(2018, 3, 1);
//        LocalDate endOfMonth = new LocalDate(2018, 3, 31);
//        LocalTime endOfDay = LocalTime.MIDNIGHT.minusMillis(1);
//        DateTimeZone zone = DateTimeZone.forOffsetHours(-10);
//
//        System.out.println("endOfDay = " + endOfDay);
//        System.out.println();
//
//        System.out.println("beginOfMonth = " + beginOfMonth);
//        System.out.println("  endOfMonth = " + endOfMonth);
//        System.out.println();
//
//        System.out.println("beginOfMonth.toDateTimeAtStartOfDay() = " + beginOfMonth.toDateTimeAtStartOfDay());
//        System.out.println("      endOfMonth.toDateTime(endOfDay) = " + endOfMonth.toDateTime(endOfDay));
//        System.out.println();
//
//        System.out.println("beginOfMonth.toDateTimeAtStartOfDay(zone) = " + beginOfMonth.toDateTimeAtStartOfDay(zone));
//        System.out.println("    endOfMonth.toDateTime(endOfDay, zone) = " + endOfMonth.toDateTime(endOfDay, zone));
//        System.out.println();
//
//        System.out.println("beginOfMonth.toDateTimeAtStartOfDay(zone).toLocalDateTime() = " + beginOfMonth.toDateTimeAtStartOfDay(zone).toLocalDateTime());
//        System.out.println("    endOfMonth.toDateTime(endOfDay, zone).toLocalDateTime() = " + endOfMonth.toDateTime(endOfDay, zone).toLocalDateTime());
//        System.out.println();
//
//        System.out.println("beginOfMonth.toDateTimeAtStartOfDay(UTC).toLocalDateTime() = " + beginOfMonth.toDateTimeAtStartOfDay(DateTimeZone.UTC).toLocalDateTime());
//        System.out.println("    endOfMonth.toDateTime(endOfDay, UTC).toLocalDateTime() = " + endOfMonth.toDateTime(endOfDay, DateTimeZone.UTC).toLocalDateTime());
//        System.out.println();
//
//        System.out.println("beginOfMonth.toDateTimeAtStartOfDay(zone).withZoneRetainFields(UTC) = " + beginOfMonth.toDateTimeAtStartOfDay(zone).withZoneRetainFields(DateTimeZone.UTC));
//        System.out.println("    endOfMonth.toDateTime(endOfDay, zone).withZoneRetainFields(UTC) = " + endOfMonth.toDateTime(endOfDay, zone).withZoneRetainFields(DateTimeZone.UTC));
//        System.out.println();
//
//        System.out.println("beginOfMonth.toDateTimeAtStartOfDay(zone).withZoneRetainFields(zone) = " + beginOfMonth.toDateTimeAtStartOfDay(zone).withZoneRetainFields(zone));
//        System.out.println("    endOfMonth.toDateTime(endOfDay, zone).withZoneRetainFields(zone) = " + endOfMonth.toDateTime(endOfDay, zone).withZoneRetainFields(zone));
//        System.out.println();
//
//        System.out.println("beginOfMonth.toDateTimeAtStartOfDay(zone).withZoneRetainFields(zone).toLocalDateTime() = " + beginOfMonth.toDateTimeAtStartOfDay(zone).withZoneRetainFields(zone).toLocalDateTime());
//        System.out.println("    endOfMonth.toDateTime(endOfDay, zone).withZoneRetainFields(zone).toLocalDateTime() = " + endOfMonth.toDateTime(endOfDay, zone).withZoneRetainFields(zone).toLocalDateTime());
//        System.out.println();

//        LocalDate localDate = new LocalDate("2018-03");
//        System.out.println(localDate);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JodaModule());
//        String json = "{\"from\":\"2018-03\",\"to\":\"2018-03\"}";
//        SomeRequestDTO dto = objectMapper.readValue(json, SomeRequestDTO.class);
//        System.out.println(dto);
//        System.out.println(String.format("%s equal %s ? %s", dto.getFrom(), dto.getTo(), dto.getFrom().equals(dto.getTo())));
//        System.out.println(String.format("%s after %s ? %s", dto.getFrom(), dto.getTo(), dto.getFrom().isAfter(dto.getTo())));
//        System.out.println(String.format("%s after %s ? %s", dto.getFrom().plusMonths(1), dto.getTo(), dto.getFrom().plusMonths(1).isAfter(dto.getTo())));

//        LocalTime endOfSchedule = new LocalTime(6, 0, 0);
//        LocalTime currentTime = new LocalTime(23, 30, 0);
//        System.out.println(endOfSchedule.isBefore(currentTime));

//        DateTime now = DateTime.now();
//        System.out.println("now: " + now);
//        System.out.println("now.withMillisOfDay(0): " + now.withMillisOfDay(0));

//        LocalTime startLocalTime = new LocalTime(12, 0, 0);
//        LocalTime endLocalTime = new LocalTime(16, 0, 0);
//        System.out.println("startLocalTime > endLocalTime ? " + (startLocalTime.compareTo(endLocalTime) > 0));
//
//        DateTime startDateTime1 = new DateTime(2018, 3, 28, 8, 0, 0, DateTimeZone.UTC);
//        DateTime endDateTime1 = startDateTime1.plusHours(4);
//
//        DateTime startDateTime2 = new DateTime(2018, 3, 28, 11, 15, 0, 0, DateTimeZone.UTC);
//        DateTime endDateTime2 = startDateTime2.plusMinutes(44);
//
//        Interval interval1 = new Interval(startDateTime1, endDateTime1);
//        Interval interval2 = new Interval(startDateTime2, endDateTime2);
//
//        System.out.println("interval1: " + interval1);
//        System.out.println("interval2: " + interval2);
//        System.out.println("interval1 intersects interval1: " + interval1.overlap(interval1));
//        System.out.println("interval1 intersects interval2: " + interval1.overlap(interval2));
//        System.out.println("interval2 intersects interval1: " + interval2.overlap(interval1));

//        DateTimeZone samara = DateTimeZone.forID("Europe/Samara");
//        System.out.println("moscow = " + DateTime.now());
//        System.out.println("samara = " + DateTime.now(samara));
//
//        System.out.println(samara.getOffset(DateTime.now()) / 1000 / 60 / 60);

        Set<String> ids = DateTimeZone.getAvailableIDs();
        for (String id : ids) {
            if (id.toLowerCase().contains("asia") || id.toLowerCase().contains("europe")) {
                DateTimeZone zone = DateTimeZone.forID(id);
                System.out.println(hours(zone) + " " + id);
            }
        }
    }

    private static double hours(DateTimeZone zone) {
        return zone.getOffset(DateTime.now()) / 1000.0 / 60 / 60;
    }
}


@Data
@NoArgsConstructor
@AllArgsConstructor
class SomeRequestDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM")
    private LocalDate from;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM")
    private LocalDate to;
}
