package edu.io.files;

import java.io.File;
import java.io.IOException;

/**
 * Created by Anton Epishin on 25.07.2017.
 */
public class FileFileExample {
    public static void main(String[] args) {

        // определяем объект для каталога
        File myFile = new File("D://TempDir//temp_file1.txt");
        System.out.println("Имя файла: " + myFile.getName());
        System.out.println("Родительский каталог: " + myFile.getParent());
        if (myFile.exists()) {
            System.out.println("Файл существует");
        } else {
            System.out.println("Файл еще не создан");
        }
        System.out.println("Размер файла: " + myFile.length());

        if (myFile.canRead()) {
            System.out.println("Файл доступен для чтения");
        } else {
            System.out.println("Файл не доступен для чтения");
        }
        if (myFile.canWrite()) {
            System.out.println("Файл доступен для записи");
        } else {
            System.out.println("Файл не доступен для записи");
        }
        // создадим новый файл
        File newFile = new File("D://TempDir//MyFile");
        try {
            boolean created = newFile.createNewFile();
            if (created) {
                System.out.println("Файл создан");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

