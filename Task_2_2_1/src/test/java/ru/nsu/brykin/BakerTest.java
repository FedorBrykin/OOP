package ru.nsu.brykin;

import org.junit.jupiter.api.Test;
import java.util.concurrent.LinkedBlockingQueue;
import static org.junit.jupiter.api.Assertions.*;

class BakerTest {
    @Test
    void testBaker() throws InterruptedException {
        Storage storage = new Storage(10);
        LinkedBlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();
        Baker baker = new Baker(1, storage, orderQueue);
        Order order = new Order(1);
        orderQueue.add(order);
        baker.start();
        Thread.sleep(100);
        assertTrue(storage.isEmpty());
        baker.interrupt();
    }
}