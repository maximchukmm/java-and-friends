package edu.tutorials.oracle.exceptions.listofnumbers;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ListOfNumbersTest {
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath("res", "OutFile.txt");
        String filename = "./res/OutFile.txt";
        ListOfNumbers listOfNumbers = new ListOfNumbers();

        if (Files.notExists(path)) {
            listOfNumbers.writeList(filename);
        } else {
            List<Integer> numbers = listOfNumbers.readList(filename);
            for (Integer i : numbers) {
                System.out.println(i);
            }
        }
    }
}
