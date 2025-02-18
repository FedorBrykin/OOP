package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * указ!.
 */
class OrderTest {
    @Test
    void testOrderCreation() {
        Order order = new Order(1);
        assertEquals(1, order.getOrderId());
    }
}