package edu.tutorials.oracle.exceptions.listofnumbers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ListOfNumbers {

    private List<Integer> list;
    private static final int SIZE = 10;

    public ListOfNumbers() {
        list = new ArrayList<Integer>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            list.add(new Integer(i));
        }
    }

    public List<Integer> readList(String filepath) {
        List<Integer> numbers = null;
        try (BufferedReader in = new BufferedReader(new FileReader(filepath))) {
            numbers = new Vector<>();
            String line;
            while ((line = in.readLine()) != null) {
                String[] splittedLine = line.split(" = ");
                numbers.add(Integer.parseInt(splittedLine[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numbers;
    }

    public void writeList(String filepath) {
        PrintWriter out = null;

        try {
            System.out.println("Entering" + " try statement");

            out = new PrintWriter(new FileWriter(filepath));
            for (int i = 0; i < SIZE; i++) {
                out.println("Value at: " + i + " = " + list.get(i));
            }
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Caught IndexOutOfBoundsException: "
                + e.getMessage());
        } catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        } finally {
            if (out != null) {
                System.out.println("Closing PrintWriter");
                out.close();
            } else {
                System.out.println("PrintWriter not open");
            }
        }
    }
}
