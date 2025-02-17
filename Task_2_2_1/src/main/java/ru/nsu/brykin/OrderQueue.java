package ru.nsu.brykin;

import java.util.Queue;
import java.util.LinkedList;

public class OrderQueue {
    private final Queue<Order> queue = new LinkedList<>();

    public synchronized void addOrder(Order order) {
        queue.add(order);
        notifyAll();
    }

    public synchronized Order takeOrder() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        return queue.poll();
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}