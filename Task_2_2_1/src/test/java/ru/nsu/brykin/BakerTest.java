package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * проверка резюме.
 */
class BakerTest {
    @Test
    void testBaker() throws InterruptedException {
        Storage storage = new Storage(10);
        OrderQueue orderQueue = new OrderQueue();
        Baker baker = new Baker(1, storage, orderQueue);
        Order order = new Order(1);
        orderQueue.addOrder(order);
        baker.start();
        Thread.sleep(100);
        assertTrue(storage.isEmpty());
        baker.interrupt();
    }
}