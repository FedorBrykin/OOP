package ru.nsu.brykin;

import java.util.Queue;
import java.util.LinkedList;

public class Storage {
    private final Queue<Order> orders;
    private final int capacity;

    public Storage(int capacity) {
        this.capacity = capacity;
        this.orders = new LinkedList<>();
    }

    public synchronized void addOrder(Order order) throws InterruptedException {
        while (orders.size() >= capacity) {
            wait();
        }
        orders.add(order);
        notifyAll();
    }

    public synchronized Order takeOrder() throws InterruptedException {
        while (orders.isEmpty()) {
            wait();
        }
        Order order = orders.poll();
        notifyAll();
        return order;
    }

    public synchronized boolean isEmpty() {
        return orders.isEmpty();
    }
}