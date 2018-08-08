package edu.tutorials.oracle.regex;

import java.io.Console;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RegexTestHarnessDelimeters {

    public static void main(String[] args) {
        Console console = System.console();
        if (console == null) {
            System.err.println("No console.");
            System.exit(1);
        }
        while (true) {
            try {
                Pattern pattern =
                    Pattern.compile(console.readLine("%nEnter your regex delimeter: "));
                Scanner splitter = new Scanner(console.readLine("Enter input string to split: "));
                splitter.useDelimiter(pattern);

                boolean found = false;
                int numOfWords = 1;
                while (splitter.hasNext()) {
                    console.format("Splitted word" +
                            " %d:" +
                            " \"%s\"%n",
                        numOfWords,
                        splitter.next());
                    found = true;
                    numOfWords++;
                }
                if (!found) {
                    console.format("Nothing to split.%n");
                }
                splitter.close();
            } catch (PatternSyntaxException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
