package edu.other;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class SortByTwoParameters {
    public static void main(String[] args) {
        List<Segment> segments = new ArrayList<>();
        segments.add(new Segment(1, 2));
        segments.add(new Segment(1, 3));
        segments.add(new Segment(1, 4));
        segments.add(new Segment(1, 5));
        segments.add(new Segment(2, 5));
        segments.add(new Segment(2, 6));

        segments.forEach(System.out::println);

        System.out.println();

        segments.sort((s1, s2) -> {
            int aCompare = s1.getA().compareTo(s2.getA());
            if (aCompare == 0) {
                return s2.getB().compareTo(s1.getB());
            }
            return aCompare;
        });

        segments.forEach(System.out::println);
    }
}

@Data
@AllArgsConstructor
class Segment {
    private Integer a;
    private Integer b;

    @Override
    public String toString() {
        return a + " " + b;
    }
}
