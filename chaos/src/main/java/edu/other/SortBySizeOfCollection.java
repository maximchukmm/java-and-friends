package edu.other;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class SortBySizeOfCollection {
    public static void main(String[] args) {
        Random random = new Random();

        List<MyData> list = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            list.add(new MyData(i, generateValues(random.nextInt(10))));
        }

        list.forEach(System.out::println);

        System.out.println();

        sortByValuesSize(list);

        list.forEach(System.out::println);
    }

    private static List<Integer> generateValues(int size) {
        List<Integer> values = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            values.add(i);
        }

        return values;
    }

    private static void sortByValuesSize(List<MyData> list) {
        list.sort(Comparator.comparing(myData -> myData.getValues().size()));
    }
}

@Data
@AllArgsConstructor
class MyData {
    private Integer field;
    List<Integer> values = new ArrayList<>();
}