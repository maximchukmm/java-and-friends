package edu.joda;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.joda.time.DateTimeZone.UTC;

class IntervalDemo {
    public static void main(String[] args) {
        Interval request = new Interval(
            new DateTime(2018, 8, 20, 9, 55, 0),
            new DateTime(2018, 8, 20, 11, 55, 0)
        );
        Interval response = new Interval(
            new DateTime(2018, 8, 20, 10, 5, 0),
            new DateTime(2018, 8, 20, 12, 5, 0)
        );

        System.out.println(intervalsSubtraction(request, response));
        System.out.println();
        System.out.println(intervalsSubtraction(response, request));
    }

    /**
     * Нахождение пересечений интервалов с указанным периодом времени без даты.
     *
     * @param intervals интервалы, с каждым из которых будет определяться наличие пересечения с указанным периодом времени
     * @param start     начало периода времени без даты
     * @param end       окончание периода времени без даты
     * @param zone      временная зона интервалов
     * @return интервалы, которые пересекаются с указанным периодом времени
     */
    static List<Interval> getOverlapsOfIntervalsWith(List<Interval> intervals, LocalTime start, LocalTime end, DateTimeZone zone) {
        if (intervals.isEmpty()) return Collections.emptyList();

        List<Interval> splitIntervalsByMidnight = splitIntervalsByLocalMidnight(intervals, zone);
        List<Interval> intersections = new ArrayList<>();
        for (Interval interval : splitIntervalsByMidnight) {
            Interval anotherInterval = new Interval(interval.getStart().withTime(start), interval.getStart().withTime(end));
            if (interval.overlaps(anotherInterval))
                intersections.add(interval.overlap(anotherInterval));
        }
        return intersections;
    }

    /**
     * Разрезает интервал, которые пересекает полночь, на два интервала.
     * <br>Например, интервал 20.01.2018 20:00:00 - 21.01.2018 02:00:00 будет разделен на два следующих интервала:
     * <li>20.01.2018 20:00:00 - 21.01.2018 00:00:00
     * <li>21.01.2018 00:00:00 - 21.01.2018 02:00:00
     *
     * @param intervals интервалы, которые будут разрены по полуночи, если они ее пересекают
     * @return интервалы, разделенные по полуночи
     */
    static List<Interval> splitIntervalsByLocalMidnight(List<Interval> intervals, DateTimeZone zone) {
        if (intervals.isEmpty()) return Collections.emptyList();

        List<Interval> splitIntervals = new ArrayList<>();
        intervals.sort(Comparator.comparing(Interval::getStart));
        DateTime currentMidnight = intervals.get(0).getStart().withZone(zone).withMillisOfDay(0).withZone(UTC);
        for (Interval interval : intervals) {
            while (currentMidnight.isBefore(interval.getStart()))
                currentMidnight = currentMidnight.plusDays(1);
            if (!interval.contains(currentMidnight)) {
                splitIntervals.add(interval);
                continue;
            }
            splitIntervals.add(interval.withEnd(currentMidnight));
            splitIntervals.add(interval.withStart(currentMidnight));
        }
        return splitIntervals;
    }

    static List<Interval> intervalsSubtraction(Interval i1, Interval i2) {
        if (i2.contains(i1)) return Collections.emptyList();

        List<Interval> subtraction = new ArrayList<>();

        if (!i1.overlaps(i2))
            subtraction.add(i1);
        else if (i1.contains(i2)) {
            subtraction.add(new Interval(i1.getStart(), i2.getStart()));
            subtraction.add(new Interval(i2.getEnd(), i1.getEnd()));
        } else if (i2.contains(i1.getStart()))
            subtraction.add(new Interval(i2.getEnd(), i1.getEnd()));
        else
            subtraction.add(new Interval(i1.getStart(), i2.getStart()));

        return subtraction;
    }
}
