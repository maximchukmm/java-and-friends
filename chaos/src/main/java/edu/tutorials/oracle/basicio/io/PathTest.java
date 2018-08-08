package edu.tutorials.oracle.basicio.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathTest {
    public static void main(String[] args) {
        Path path = Paths.get(System.getProperty("user.dir"), "res", "test.txt");


        if (Files.exists(path)) {
            System.out.println("File " +
                path.getFileName() +
                " exists.");
            String text = "\nSome text.";
            try {
                Files.write(path, text.getBytes());
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } else {
            System.out.println("File " +
                path.getFileName() +
                " not found in " +
                path.getParent());
        }
    }
}
