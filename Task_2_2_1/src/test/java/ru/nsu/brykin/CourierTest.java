package ru.nsu.brykin;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CourierTest {
    @Test
    void testCourier() throws InterruptedException {
        Storage storage = new Storage(10);
        Courier courier = new Courier(1, 1, storage, null);
        Order order = new Order(1);
        storage.addOrder(order);
        courier.start();
        Thread.sleep(100);
        assertTrue(storage.isEmpty());
        courier.interrupt();
    }
}