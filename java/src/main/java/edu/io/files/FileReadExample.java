package edu.io.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Anton Epishin on 25.07.2017.
 */
public class FileReadExample {
    public static void main(String[] args) {
        try {
            readUsingFileReader("D:\\TempDir\\temp_file1.txt");
            readUsingBufferedReader("D:\\TempDir\\temp_file1.txt", Charset.defaultCharset());
            readUsingScanner("D:\\TempDir\\temp_file1.txt");
            readUsingFiles("D:\\TempDir\\temp_file1.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // используем FileReader
    private static void readUsingFileReader(String fileName) throws IOException {
        File file = new File(fileName);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine()) != null) {
            //обрабатываем считанную строку - пишем ее в консоль
            System.out.println("readUsingFileReader. File contains : " + line);
        }
        br.close();
        fr.close();

    }

    // используем BufferedReader
    private static void readUsingBufferedReader(String fileName, Charset cs) throws IOException {
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, cs);
        BufferedReader br = new BufferedReader(isr);
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println("readUsingBufferedReader. File contains : " + line);
        }
        br.close();

    }

    // используем Scaner
    private static void readUsingScanner(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        Scanner scanner = new Scanner(path);
        //читаем построчно
        while (scanner.hasNextLine()) {
            System.out.println("readUsingScanner. File contains : " + scanner.nextLine());
        }
    }

    // используем Files
    private static void readUsingFiles(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        //считываем содержимое файла в массив байт
        //byte[] bytes = Files.readAllBytes(path);
        //считываем содержимое файла в список строк
        List<String> allLines = Files.readAllLines(path, StandardCharsets.UTF_8);
        System.out.println("readUsingFiles. File contains : " + allLines);
    }

}

