package edu.io.files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Anton Epishin on 25.07.2017.
 */
public class FileWriteExample {
    public static void main(String[] args) {
        writeUsingOutputStream("UsingOutputStream");
        writeUsingFiles("UsingFiles");
        writeUsingBufferedWriter("UsingBufferedWriter", 3);
        writeUsingFileWriter("UsingFileWriter");
    }

    private static void writeUsingOutputStream(String data) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(new File("D:\\TempDir\\write.txt"));
            os.write(data.getBytes(), 0, data.length());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // запись в файл с помощью Files
    private static void writeUsingFiles(String data) {
        try {
            Files.write(Paths.get("D:\\TempDir\\write.txt"), data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // запись в файл с помощью BufferedWriter
    private static void writeUsingBufferedWriter(String data, int noOfLines) {
        File file = new File("D:\\TempDir\\write.txt");
        FileWriter fr = null;
        BufferedWriter br = null;
        String dataWithNewLine = data + System.getProperty("line.separator");
        try {
            fr = new FileWriter(file);
            br = new BufferedWriter(fr);
            for (int i = noOfLines; i > 0; i--) {
                br.write(dataWithNewLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    // запись файл с помощью FileWriter
    private static void writeUsingFileWriter(String data) {
        File file = new File("D:\\TempDir\\write.txt");
        FileWriter fr = null;
        try {
            fr = new FileWriter(file);
            fr.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

