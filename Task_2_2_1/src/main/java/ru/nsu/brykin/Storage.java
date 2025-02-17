package ru.nsu.brykin;

import java.util.LinkedList;
import java.util.Queue;

/**
 * хранилише.
 */
public class Storage {
    private final Queue<Order> orders;
    private final int capacity;

    /**
     * клююююч!.
     */
    public Storage(int capacity) {
        this.capacity = capacity;
        this.orders = new LinkedList<>();
    }

    /**
     * +заказ.
     */
    public synchronized void addOrder(Order order) throws InterruptedException {
        while (orders.size() >= capacity) {
            wait();
        }
        orders.add(order);
        notifyAll();
    }

    /**
     * "я возьму!".
     */
    public synchronized Order takeOrder() throws InterruptedException {
        while (orders.isEmpty()) {
            wait();
        }
        Order order = orders.poll();
        notifyAll();
        return order;
    }

    /**
     * всё?.
     */
    public synchronized boolean isEmpty() {
        return orders.isEmpty();
    }
}