package edu.tutorials.oracle.exceptions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;


public class ModifiedCat {

    public static void cat(File file) {
        RandomAccessFile input = null;
        String line = null;
        try {
            input = new RandomAccessFile(file, "r");
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
            return;
        } catch (FileNotFoundException fnf) {
            System.err.println("File: " + file.getName() + " not found.");
        } catch (IOException io) {
            System.err.println(io.toString());
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException io) {
                    System.err.println(io.toString());
                }
            }
            System.out.println("Some finally code...");
        }
        System.out.println("Some code here...");
    }

    public static void main(String[] args) {
        cat(new File("./res/OutFile.txt"));
    }
}
