package ru.nsu.brykin;

import java.util.Queue;

public class Baker extends Thread {
    private final int speed;
    private final Storage storage;
    private final Queue<Order> orderQueue;

    public Baker(int speed, Storage storage, Queue<Order> orderQueue) {
        this.speed = speed;
        this.storage = storage;
        this.orderQueue = orderQueue;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            Order order;
            synchronized (orderQueue) {
                while (orderQueue.isEmpty()) {
                    try {
                        orderQueue.wait();
                    } catch (InterruptedException e) {
                        interrupt();
                        return;
                    }
                }
                order = orderQueue.poll();
                orderQueue.notifyAll();
            }

            try {
                Thread.sleep(speed * 1000L); // Имитация приготовления пиццы
                storage.addOrder(order);
                System.out.println("[" + order.getOrderId() + "] [completed]");
            } catch (InterruptedException e) {
                interrupt();
                return;
            }
        }
    }
}