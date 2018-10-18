package edu.joda;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class IntervalUtils {
    private IntervalUtils() {
        throw new RuntimeException("IntervalUtils is an utility class. It cannot be instantiated.");
    }

    public static List<Interval> intervalsSubtraction(Interval i1, Interval i2) {
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

    //todo test isIntervalsSortedByStart
    public static boolean isIntervalsSortedByStart(List<Interval> intervals) {
        for (int i = 0; i < intervals.size() - 1; i++) {
            DateTime currentStart = intervals.get(i).getStart();
            DateTime nextStart = intervals.get(i + 1).getStart();
            if (currentStart.compareTo(nextStart) >= 1)
                return false;
        }
        return true;
    }

    public static long getDurationInSeconds(Interval interval) {
        List<Interval> intervals = splitIntervalIfNecessary(interval);

        return intervals
            .stream()
            .mapToLong(i -> i.toDuration().getStandardSeconds())
            .sum();
    }

    //todo test splitIntervalIfNecessary
    public static List<Interval> splitIntervalIfNecessary(Interval interval) {
        long daysBetween = interval.toDuration().getStandardDays();

        // если интервал длительностью меньше 30 лет, тогда интервал не разрезаем,
        // т.к. 30 лет в секундах поместятся в тип int
        // и метод Seconds.secondsBetween(start, end).getSeconds() не бросит исключение
        if (daysBetween < 30 * 366) {
            return Collections.singletonList(interval);
        }

        List<Interval> intervals = new ArrayList<>();
        DateTime start = interval.getStart();
        DateTime end = interval.getEnd();

        while (start.isBefore(end)) {
            // разрезаем интервал на части по 15 лет каждая
            DateTime newEnd = start.plusDays(15 * 366);

            // если не вышли за правую границу интервала, добавляем новую часть
            if (newEnd.isBefore(end) || newEnd.isEqual(end)) {
                intervals.add(new Interval(start, newEnd));
            } else {
                intervals.add(new Interval(start, end));
                break;
            }

            start = newEnd;
        }

        return intervals;
    }
}
