package edu.reflection.privatefieldwithoutsetter;

import java.lang.reflect.Field;

public class PrivateFieldWithoutSetterDemo {
    public static void main(String[] args) throws Exception {
        ClassWithPrivateFieldWithoutSetter inst = new ClassWithPrivateFieldWithoutSetter();

        System.out.println(inst.getNumber());

        Field field = ClassWithPrivateFieldWithoutSetter.class.getDeclaredField("number");
        field.setAccessible(true);
        field.setInt(inst, 333);

        System.out.println(inst.getNumber());

        System.out.println(field.getInt(inst));
    }
}
