package edu.lambdaandstream.experiments;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RemoveIfHowEachIterationWorksDemo {
    public static void main(String[] args) {
        List<MyNumber> allNumbers = new ArrayList<>();

        for (int i = 0; i < 10; i++)
            allNumbers.add(new MyNumber(i));

        List<MyNumber> numbersToDelete = new ArrayList<>();
        numbersToDelete.add(new MyNumber(1));
        numbersToDelete.add(new MyNumber(1));
        numbersToDelete.add(new MyNumber(3));
        numbersToDelete.add(new MyNumber(3));
        numbersToDelete.add(new MyNumber(5));
        numbersToDelete.add(new MyNumber(5));
        numbersToDelete.add(new MyNumber(7));
        numbersToDelete.add(new MyNumber(7));
        numbersToDelete.add(new MyNumber(9));
        numbersToDelete.add(new MyNumber(9));
        numbersToDelete.add(new MyNumber(11));
        numbersToDelete.add(new MyNumber(11));

        System.out.println(allNumbers);
        System.out.println(numbersToDelete);

        System.out.println();

        allNumbers.removeIf(n ->
            numbersToDelete
                .stream()
                .map(MyNumber::getNumberWithLog)
                .collect(Collectors.toSet())
                .contains(n.getNumber()));

        System.out.println(allNumbers);
    }

    private static class MyNumber {
        int number;

        private MyNumber(int number) {
            this.number = number;
        }

        private int getNumberWithLog() {
            System.out.println("Get number : " + number);
            return number;
        }

        private int getNumber() {
            return number;
        }

        @Override
        public String toString() {
            return String.valueOf(number);
        }
    }
}
