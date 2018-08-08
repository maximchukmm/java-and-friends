package edu.lambdaandstream;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class GetListFromRange {
    public static void main(String[] args) {
        List<Integer> list = IntStream.range(1, 10)
            .boxed()
            .collect(Collectors.toList());

        list.forEach(System.out::println);
    }
}
