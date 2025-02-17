package ru.nsu.brykin;

public class Baker extends Thread {
    private final int speed;
    private final Storage storage;
    private final OrderQueue orderQueue;

    public Baker(int speed, Storage storage, OrderQueue orderQueue) {
        this.speed = speed;
        this.storage = storage;
        this.orderQueue = orderQueue;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                Order order = orderQueue.takeOrder();
                Thread.sleep(speed * 1000L);
                storage.addOrder(order);
                System.out.println("[" + order.getOrderId() + "] [готово]");
            } catch (InterruptedException e) {
                interrupt();
                return;
            }
        }
    }
}