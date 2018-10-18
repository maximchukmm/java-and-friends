package edu.joda;

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
}
