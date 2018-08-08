package edu.lambdaandstream;

import edu.util.Print;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class GroupingDemo {
    public static void main(String[] args) {
        List<SomeData> list = new ArrayList<>();
        list.add(new SomeData(1, "data 1-1"));
        list.add(new SomeData(1, "data 1-2"));
        list.add(new SomeData(1, "data 1-3"));
        list.add(new SomeData(2, "data 2-1"));
        list.add(new SomeData(2, "data 2-2"));
        list.add(new SomeData(2, "data 2-3"));
        list.add(new SomeData(3, "data 3-1"));
        list.add(new SomeData(3, "data 3-2"));
        list.add(new SomeData(4, "data 4-1"));

        Map<Integer, List<SomeData>> groupedByClass = list.stream().collect(Collectors.groupingBy(SomeData::getNumber));
        Print.map(groupedByClass);

        System.out.println();

        Map<Integer, List<String>> groupedByDataField = list
            .stream()
            .collect(Collectors.groupingBy(
                SomeData::getNumber,
                Collectors.mapping(SomeData::getData, Collectors.toList())
            ));
        Print.map(groupedByDataField);
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class SomeData {
    private Integer number;
    private String data;
}
