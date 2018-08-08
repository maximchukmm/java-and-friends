package edu.exceptions;

import java.io.FileWriter;
import java.io.IOException;

class TryWithResourcesDemo {
    public static void main(String[] args) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("file.txt");
            fileWriter.write(1);
        } catch (IOException e) {
            e.printStackTrace();
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        try (FileWriter fileWriter1 = new FileWriter("file.txt")) {
            fileWriter1.write(2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
