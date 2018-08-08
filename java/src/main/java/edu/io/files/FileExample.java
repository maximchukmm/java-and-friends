package edu.io.files;

import java.io.File;

/**
 * Created by Anton Epishin on 25.07.2017.
 */
public class FileExample {

    public static void main(String args[]) {

        // создаем объект File для каталога
        File dir1 = new File("C://SomeDir");
        // создаем объекты для файлов, которые находятся в каталоге
        File file1 = new File("C://SomeDir", "Hello.txt");
        File file2 = new File(dir1, "Hello2.txt");

    }
}
