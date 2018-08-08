package edu.io.files;

import java.io.File;

/**
 * Created by Anton Epishin on 25.07.2017.
 */
public class FileDirExample {
    public static void main(String[] args) {
        // определяем объект для каталога
        File dir = new File("D://TempDir");
        // если объект представляет каталог
        if (dir.isDirectory()) {
            // получаем все вложенные объекты в каталоге
            for (File item : dir.listFiles()) {
                if (item.isDirectory()) {
                    System.out.println(item.getName() + "  \tкаталог");
                } else {
                    System.out.println(item.getName() + "\tфайл");
                }
            }
        }
    }

}
