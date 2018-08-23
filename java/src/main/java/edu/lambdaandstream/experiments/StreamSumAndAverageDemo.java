package edu.lambdaandstream.experiments;

import java.util.ArrayList;
import java.util.List;

public class StreamSumAndAverageDemo {
    public static void main(String[] args) {
        List<Double> doubles = new ArrayList<>();
        System.out.println(doubles.stream().mapToDouble(Double::doubleValue).sum());
        System.out.println(doubles.stream().mapToDouble(Double::doubleValue).average());

        System.out.println();

        doubles.add(Double.NaN);
        System.out.println(doubles.stream().mapToDouble(Double::doubleValue).sum());
        System.out.println(doubles.stream().mapToDouble(Double::doubleValue).average());
    }
}
