package ru.nsu.brykin;

public class Pizzeria {
    private final OrderQueue orderQueue = new OrderQueue();
    private final Storage storage;
    private final Baker[] bakers;
    private final Courier[] couriers;
    private boolean isOpen = true;
    private int activeOrders = 0; // Счетчик активных заказов

    public Pizzeria(int N, int M, int T, int[] bakerSpeeds, int[] courierCapacities, int[] courierDeliveryTimes) {
        this.storage = new Storage(T);
        this.bakers = new Baker[N];
        for (int i = 0; i < N; i++) {
            bakers[i] = new Baker(bakerSpeeds[i], storage, orderQueue);
        }
        this.couriers = new Courier[M];
        for (int i = 0; i < M; i++) {
            couriers[i] = new Courier(courierCapacities[i], courierDeliveryTimes[i], storage, this);
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
            System.out.println("[" + order.getOrderId() + "] [заказ отклонен: пиццерия закрыта]");
            return;
        }
        orderQueue.addOrder(order);
        synchronized (this) {
            activeOrders++; // Увеличиваем счетчик активных заказов
        }
    }

    // Уменьшаем счетчик активных заказов
    public synchronized void completeOrder() {
        activeOrders--;
        notifyAll();
    }

    public boolean isWorkCompleted() {
        synchronized (orderQueue) {
            synchronized (storage) {
                return orderQueue.isEmpty() && storage.isEmpty() && activeOrders == 0;
            }
        }
    }
}