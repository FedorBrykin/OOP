package ru.nsu.brykin;

/**
 * курьер в пиццерии.
 */
public class Courier extends Thread {
    private final int trunkCapacity;
    private final int deliveryTime;
    private final Storage storage;
    private final Pizzeria pizzeria;

    /**
     * курьер.
     */
    public Courier(int trunkCapacity, int deliveryTime, Storage storage, Pizzeria pizzeria) {
        this.trunkCapacity = trunkCapacity;
        this.deliveryTime = deliveryTime;
        this.storage = storage;
        this.pizzeria = pizzeria;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                Order order = storage.takeOrder();
                Thread.sleep(deliveryTime * 1000L);
                System.out.println("[" + order.getOrderId() + "] [доставлено]");
                pizzeria.completeOrder();
            } catch (InterruptedException e) {
                interrupt();
                return;
            }
        }
    }
}