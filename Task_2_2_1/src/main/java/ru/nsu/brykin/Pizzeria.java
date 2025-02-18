package ru.nsu.brykin;

/**
 * пиццерия.
 */
public class Pizzeria {
    private final OrderQueue orderQueue = new OrderQueue();
    private final Storage storage;
    private final Baker[] bakers;
    private final Courier[] couriers;
    private boolean isOpen = true;
    private int activeOrders = 0; // Счетчик активных заказов

    /**
     * как всё устроено.
     */
    public Pizzeria(int n, int m, int t, int[] bakerSpeeds, int[] courierCapacities,
                    int[] courierDeliveryTimes) {
        this.storage = new Storage(t);
        this.bakers = new Baker[n];
        for (int i = 0; i < n; i++) {
            bakers[i] = new Baker(bakerSpeeds[i], storage, orderQueue);
        }
        this.couriers = new Courier[m];
        for (int i = 0; i < m; i++) {
            couriers[i] = new Courier(courierCapacities[i], courierDeliveryTimes[i],
                    storage, this);
        }
    }

    /**
     * мы открыты.
     */
    public void start() {
        for (Baker baker : bakers) {
            baker.start();
        }
        for (Courier courier : couriers) {
            courier.start();
        }
    }

    /**
     * мы закрыты.
     */
    public void stop() {
        isOpen = false;
        for (Baker baker : bakers) {
            baker.interrupt();
        }
        for (Courier courier : couriers) {
            courier.interrupt();
        }
    }

    /**
     * заказ принят(или нет?).
     */
    public void placeOrder(Order order) {
        if (!isOpen) {
            System.out.println("[" + order.getOrderId()
                    + "] [заказ отклонен: пиццерия закрыта]");
            return;
        }
        orderQueue.addOrder(order);
        synchronized (this) {
            activeOrders++; // Увеличиваем счетчик активных заказов
        }
    }

    /**
     * заказ выполнен.
     */
    // Уменьшаем счетчик активных заказов
    public synchronized void completeOrder() {
        activeOrders--;
    }

    /**
     * всё, пошли домой.
     */
    public boolean isWorkCompleted() {
        synchronized (orderQueue) {
            synchronized (storage) {
                return orderQueue.isEmpty() && storage.isEmpty() && activeOrders == 0;
            }
        }
    }
}