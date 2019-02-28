package edu.other;

import java.util.stream.DoubleStream;

public class LeastSquaresDemo {
    public static void main(String[] args) {
        double[] x = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        double[] y = {8, 6, 10, 6, 10, 13, 9, 11, 15, 17};
        int n = x.length;

        double sumX = .0;
        double sumY = .0;
        double sumXY = .0;
        double sumSquareX = .0;
        double squareSumX;
        double averageX;
        double averageY;

        for (int i = 0; i < x.length; i++) {
            sumX += x[i];
            sumY += y[i];
            sumXY += x[i] * y[i];
            sumSquareX += x[i] * x[i];
        }

        squareSumX = sumX * sumX;
        averageX = sumX / n;
        averageY = sumY / n;

        double b = (n * sumXY - sumX * sumY) / (n * sumSquareX - squareSumX);
        double a = averageY - b * averageX;

        System.out.println(String.format("y = %f + %f * x", a, b));

        System.out.println();

        System.out.println("x\t\t\t\ty\t\t\t\tforecast y");

        for (int i = 0; i < x.length; i++) {
            System.out.println(String.format("%f\t\t%f\t\t%f", x[i], y[i], a + b * x[i]));
        }
    }
}
