package ru.nsu.brykin;


/**
 * заказы в пиццерии.
 */
public class Order {
    private final int orderId;

    /**
     * заказы.
     */
    public Order(int orderId) {
        this.orderId = orderId;
    }

    /**
     * номер заказа.
     */
    public int getOrderId() {
        return orderId;
    }
}