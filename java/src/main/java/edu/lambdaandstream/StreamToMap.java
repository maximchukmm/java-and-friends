package edu.lambdaandstream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class StreamToMap {
    public static void main(String[] args) {
        List<IntegerToString> integers = new ArrayList<>();
        integers.add(new IntegerToString(1));
        integers.add(new IntegerToString(2));
        integers.add(new IntegerToString(3));
        integers.add(new IntegerToString(4));
        integers.add(new IntegerToString(5));
        integers.add(new IntegerToString(777));


        Map<String, Integer> mapSavingNew = integers.stream().collect(Collectors.toMap(IntegerToString::getDescribeOfNumber, (v) -> v.getNumber(), (o, n) -> n));
        Map<String, Integer> mapSavingOld = integers.stream().collect(Collectors.toMap(IntegerToString::getDescribeOfNumber, (v) -> v.getNumber(), (o, n) -> o));

        System.out.println("Map created");
    }
}

class IntegerToString {
    private final Integer number;

    IntegerToString(Integer number) {
        this.number = number;
    }

    public Integer getNumber() {
        return number;
    }

    String getDescribeOfNumber() {
        switch (number) {
            case 1:
                return "One";
            case 2:
                return "Two";
            case 3:
                return "Three";
            default:
                return "NaN";
        }
    }
}


