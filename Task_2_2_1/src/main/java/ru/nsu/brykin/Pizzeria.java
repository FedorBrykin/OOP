package ru.nsu.brykin;

import java.util.Queue;
import java.util.LinkedList;

public class Pizzeria {
    private final Queue<Order> orderQueue = new LinkedList<>();
    private final Storage storage;
    private final Baker[] bakers;
    private final Courier[] couriers;
    private boolean isOpen = true;
    private int activeOrders = 0;

    public Pizzeria(int N, int M, int T, int[] bakerSpeeds, int[] courierCapacities) {
        this.storage = new Storage(T);
        this.bakers = new Baker[N];
        for (int i = 0; i < N; i++) {
            bakers[i] = new Baker(bakerSpeeds[i], storage, orderQueue);
        }
        this.couriers = new Courier[M];
        for (int i = 0; i < M; i++) {
            couriers[i] = new Courier(courierCapacities[i], storage, this);
        }
    }

    public void start() {
        for (Baker baker : bakers) {
            baker.start();
        }
        for (Courier courier : couriers) {
            courier.start();
        }
    }

    public void stop() {
        isOpen = false;
        for (Baker baker : bakers) {
            baker.interrupt();
        }
        for (Courier courier : couriers) {
            courier.interrupt();
        }
    }

    public void placeOrder(Order order) {
        if (!isOpen) {
            System.out.println("[" + order.getOrderId() + "] [declined]");
            return;
        }

        synchronized (this) {
            activeOrders++;
        }

        synchronized (orderQueue) {
            orderQueue.add(order);
            orderQueue.notifyAll();
        }

        System.out.println("[" + order.getOrderId() + "] [accepted]");
    }

    public synchronized void completeOrder() {
        activeOrders--;
        notifyAll();
    }

    public synchronized boolean isWorkCompleted() {
        synchronized (orderQueue) {
            synchronized (storage) {
                return orderQueue.isEmpty() && storage.isEmpty() && activeOrders == 0;
            }
        }
    }
}