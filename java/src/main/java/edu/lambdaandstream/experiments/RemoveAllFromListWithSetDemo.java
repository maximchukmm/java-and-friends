package edu.lambdaandstream.experiments;

import edu.util.Print;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RemoveAllFromListWithSetDemo {
    public static void main(String[] args) {
        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 20; i++)
            integers.add(i);

        Print.collection(integers, "before remove");

        integers.removeAll(
            integers
                .stream()
                .filter(i -> i >= 10)
                .collect(Collectors.toSet())
        );

        Print.collection(integers, "after remove");
    }
}
