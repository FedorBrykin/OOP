package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * открывайте хранилище.
 */
class StorageTest {
    @Test
    void testAddAndTakeOrder() throws InterruptedException {
        Storage storage = new Storage(2);
        Order order1 = new Order(1);
        Order order2 = new Order(2);
        storage.addOrder(order1);
        storage.addOrder(order2);
        assertEquals(order1, storage.takeOrder());
        assertEquals(order2, storage.takeOrder());
        assertTrue(storage.isEmpty());
    }

    @Test
    void testStorageCapacity() throws InterruptedException {
        Storage storage = new Storage(1);
        Order order1 = new Order(1);
        Order order2 = new Order(2);
        storage.addOrder(order1);
        Thread thread = new Thread(() -> {
            try {
                storage.addOrder(order2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        thread.start();
        Thread.sleep(100);
        assertEquals(Thread.State.WAITING, thread.getState());
        storage.takeOrder();
        thread.join();
        assertFalse(storage.isEmpty());
    }
}