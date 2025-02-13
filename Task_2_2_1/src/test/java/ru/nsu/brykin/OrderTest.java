package ru.nsu.brykin;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    @Test
    void testOrderCreation() {
        Order order = new Order(1);
        assertEquals(1, order.getOrderId());
    }
}