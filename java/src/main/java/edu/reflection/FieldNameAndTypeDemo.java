package edu.reflection;

import java.lang.reflect.Field;

public class FieldNameAndTypeDemo {
    private int number;
    private String text;

    public FieldNameAndTypeDemo(int number, String text) {
        this.number = number;
        this.text = text;
    }

    public static void main(String[] args) {
        Field[] fields = FieldNameAndTypeDemo.class.getDeclaredFields();

        for (Field field : fields) {
            System.out.println("Name: " + field.getName() + " Type: " + field.getType().getSimpleName());
        }
    }
}
