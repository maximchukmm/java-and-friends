package edu.tutorials.oracle.lambda;

public class CompareAnonymAndLambdaImpl {
    public static void main(String[] args) {

        // RunnableImpl
        Runnable r0 = new RunnableImpl();

        // Anonymous Runnable
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Runnable ONE");
            }
        };

        // Lambda Runnable
        Runnable r2 = () -> System.out.println("Runnable TWO");

        r1.run();
        r2.run();
    }
}
