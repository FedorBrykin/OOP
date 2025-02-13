package ru.nsu.brykin;

public class Courier extends Thread {
    private final int trunkCapacity;
    private final Storage storage;
    private final Pizzeria pizzeria;

    public Courier(int trunkCapacity, Storage storage, Pizzeria pizzeria) {
        this.trunkCapacity = trunkCapacity;
        this.storage = storage;
        this.pizzeria = pizzeria;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                Order order = storage.takeOrder();
                Thread.sleep(1000);
                System.out.println("[" + order.getOrderId() + "] [delivered]");
                pizzeria.completeOrder();
            } catch (InterruptedException e) {
                interrupt();
                return;
            }
        }
    }
}