package ru.nsu.brykin;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StorageTest {
    @Test
    void testAddAndTakeOrder() throws InterruptedException {
        Storage storage = new Storage(2);
        Order order1 = new Order(1);
        Order order2 = new Order(2);

        // Добавляем заказы
        storage.addOrder(order1);
        storage.addOrder(order2);

        // Проверяем, что заказы добавлены
        assertEquals(order1, storage.takeOrder());
        assertEquals(order2, storage.takeOrder());

        // Проверяем, что склад пуст
        assertTrue(storage.isEmpty());
    }

    @Test
    void testStorageCapacity() throws InterruptedException {
        Storage storage = new Storage(1);
        Order order1 = new Order(1);
        Order order2 = new Order(2);

        // Добавляем первый заказ
        storage.addOrder(order1);

        // Пытаемся добавить второй заказ (должен быть заблокирован)
        Thread thread = new Thread(() -> {
            try {
                storage.addOrder(order2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        thread.start();

        // Ждем, чтобы убедиться, что поток заблокирован
        Thread.sleep(100);
        assertEquals(Thread.State.WAITING, thread.getState());

        // Освобождаем место на складе
        storage.takeOrder();

        // Ждем завершения потока
        thread.join();
        assertFalse(storage.isEmpty());
    }
}