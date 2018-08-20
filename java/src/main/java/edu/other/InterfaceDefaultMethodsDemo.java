package edu.other;

public class InterfaceDefaultMethodsDemo {
    public static void main(String[] args) {
        UseDefaultImplementation useDefaultImplementation = new UseDefaultImplementation();
        useDefaultImplementation.printMessage("message 1");
        System.out.println(useDefaultImplementation.processIntegers(1, 2));

        System.out.println();

        OverrideDefaultImplementation overrideDefaultImplementation = new OverrideDefaultImplementation();
        overrideDefaultImplementation.printMessage("message 2");
        System.out.println(overrideDefaultImplementation.processIntegers(3, 4));
    }
}

class UseDefaultImplementation implements MyInterface {

}

class OverrideDefaultImplementation implements MyInterface {
    @Override
    public void printMessage(String message) {
        System.out.println("Hello, " + message + "!");
    }

    @Override
    public int processIntegers(int i1, int i2) {
        return i1 * i2;
    }
}

interface MyInterface {
    default void printMessage(String message) {
        System.out.println("Default printMessage");
        System.out.println(message);
    }

    default int processIntegers(int i1, int i2) {
        System.out.println("Default processIntegers");
        return 0;
    }
}
