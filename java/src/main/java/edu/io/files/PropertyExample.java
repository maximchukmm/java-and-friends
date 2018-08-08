package edu.io.files;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Anton Epishin on 25.07.2017.
 */
public class PropertyExample {
    public static void main(String[] args) {

        FileInputStream fis;
        Properties property = new Properties();

        try {
            fis = new FileInputStream("D:\\TempDir\\property.txt");
            property.load(fis);

            String host = property.getProperty("db.host");
            String login = property.getProperty("db.login");
            String password = property.getProperty("db.password");

            System.out.println("HOST: " + host
                + ", LOGIN: " + login
                + ", PASSWORD: " + password);

        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсутствует!");
        }

    }
}
