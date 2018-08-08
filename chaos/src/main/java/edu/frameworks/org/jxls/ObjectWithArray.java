package edu.frameworks.org.jxls;

import java.util.ArrayList;
import java.util.List;

public class ObjectWithArray {
    private String now;
    private List<Integer> numbers = new ArrayList<>();

    public ObjectWithArray(String now) {
        this.now = now;
    }

    public void addNumber(int number) {
        numbers.add(number);
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    public String getNow() {
        return now;
    }
}