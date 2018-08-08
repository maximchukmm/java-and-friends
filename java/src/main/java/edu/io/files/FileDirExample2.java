package edu.io.files;

import java.io.File;

/**
 * Created by Anton Epishin on 25.07.2017.
 */
public class FileDirExample2 {
    public static void main(String[] args) {

        // определяем объект для каталога
        File dir = new File("C://NewDir");
        boolean created = dir.mkdir();
        if (created) {
            System.out.println("Каталог успешно создан");
        }
        // переименуем каталог
        File newDir = new File("C://NewDirRenamed");
        dir.renameTo(newDir);
        // удалим каталог
        boolean deleted = newDir.delete();
        if (deleted) {
            System.out.println("Каталог удален");
        }
    }


}
