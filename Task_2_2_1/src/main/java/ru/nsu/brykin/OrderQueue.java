package ru.nsu.brykin;

import java.util.LinkedList;
import java.util.Queue;

/**
 * очередь.
 */
public class OrderQueue {
    private final Queue<Order> queue = new LinkedList<>();

    /**
     * +заказ.
     */
    public synchronized void addOrder(Order order) {
        queue.add(order);
        notifyAll();
    }

    /**
     * заказ принят.
     */
    public synchronized Order takeOrder() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        return queue.poll();
    }

    /**
     * empty?.
     */
    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}