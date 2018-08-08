package edu.lambdaandstream;

import edu.util.Print;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class GroupingResolvingConflictsDemo {
    public static void main(String[] args) {
        List<GroupResolvingConflictDemo> list = new ArrayList<>();
        list.add(new GroupResolvingConflictDemo(1, 1));
        list.add(new GroupResolvingConflictDemo(1, 1));
        list.add(new GroupResolvingConflictDemo(1, 1));
        list.add(new GroupResolvingConflictDemo(1, 1));
        list.add(new GroupResolvingConflictDemo(2, 1));
        list.add(new GroupResolvingConflictDemo(2, 1));
        list.add(new GroupResolvingConflictDemo(2, 1));
        list.add(new GroupResolvingConflictDemo(3, 1));
        list.add(new GroupResolvingConflictDemo(3, 1));
        list.add(new GroupResolvingConflictDemo(4, 1));

        Map<Integer, List<GroupResolvingConflictDemo>> groupByNumber = list.stream().collect(Collectors.groupingBy(GroupResolvingConflictDemo::getNumber));
        Print.map(groupByNumber);

        System.out.println();

        Map<Integer, Integer> map1 = list.stream()
            .collect(Collectors.toMap(
                GroupResolvingConflictDemo::getNumber,
                GroupResolvingConflictDemo::getData,
                (oldValue, newValue) -> oldValue + newValue
            ));
        for (Map.Entry<Integer, Integer> entry : map1.entrySet()) {
            System.out.println(entry);
        }

        System.out.println();

        Map<Integer, String> map2 = list
            .stream()
            .collect(Collectors.toMap(
                GroupResolvingConflictDemo::getNumber,
                v -> integerToString(v, "s"),
                (o, n) -> o + " " + n
            ));
        for (Map.Entry<Integer, String> entry : map2.entrySet()) {
            System.out.println(entry);
        }
    }

    private static String integerToString(GroupResolvingConflictDemo data, String str) {
        return data.getData().toString() + " " + str;
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class GroupResolvingConflictDemo {
    private Integer number;
    private Integer data;
}