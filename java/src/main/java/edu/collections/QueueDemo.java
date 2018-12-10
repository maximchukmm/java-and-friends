package edu.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class QueueDemo {
    public static void main(String[] args) {
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        integers.add(4);

        System.out.println(integers);

        System.out.println();

        Queue<Integer> queue = new PriorityQueue<>(integers);

        while (!queue.isEmpty()) {
            System.out.print(queue.poll() + ", ");
        }

        System.out.println();
    }
}
