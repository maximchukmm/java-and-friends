package edu.other;

import lombok.AllArgsConstructor;
import lombok.Data;

class CloneableDemo {
    public static void main(String[] args) throws CloneNotSupportedException {
        Point point = new Point(10, 10);
        Point clone = (Point) point.clone();

        System.out.println("point = " + point);
        System.out.println("clone = " + clone);
    }
}

@Data
@AllArgsConstructor
class Point implements Cloneable {
    private int x;
    private int y;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
