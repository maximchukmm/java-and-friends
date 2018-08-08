package edu.tutorials.oracle.basicio.tests;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;

public class Test_CopyLines {
    public static void main(String[] args) throws IOException {

        LineNumberReader inputStream = null;
        PrintWriter outputStream = null;

        try {
            inputStream = new LineNumberReader(new FileReader("resources\\xanadu.txt"));
            outputStream = new PrintWriter(new FileWriter("resources\\outagain_02.txt"));

            int i = 10;

            String l;
            while ((l = inputStream.readLine()) != null) {
                if (i > 0 && i % 2 == 0) {
                    inputStream.mark(0);
                    i--;
                } else if (i > 0) {
                    inputStream.reset();
                    i--;
                }
                outputStream.println(l);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            if (inputStream != null) {
                System.out.println(inputStream.getLineNumber());
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
}
