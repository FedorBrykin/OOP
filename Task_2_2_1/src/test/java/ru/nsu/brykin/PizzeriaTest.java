package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * тестики.
 */
class PizzeriaTest {
    @Test
    void testPizzeriaNoOrders() throws InterruptedException {
        int N = 2;
        int M = 2;
        int T = 5;
        int[] bakerSpeeds = {1, 2};
        int[] courierCapacities = {1, 2};
        int[] courierDeliveryTimes = {1, 1};

        Pizzeria pizzeria = new Pizzeria(N, M, T, bakerSpeeds, courierCapacities, courierDeliveryTimes);
        pizzeria.start();
        Thread.sleep(1000);
        pizzeria.stop();
        assertTrue(pizzeria.isWorkCompleted());
    }

    @Test
    void testPizzeriaManyOrders() throws InterruptedException {
        int N = 2;
        int M = 2;
        int T = 5;
        int[] bakerSpeeds = {1, 2};
        int[] courierCapacities = {1, 2};
        int[] courierDeliveryTimes = {1, 1};
        Pizzeria pizzeria = new Pizzeria(N, M, T, bakerSpeeds, courierCapacities, courierDeliveryTimes);
        pizzeria.start();
        for (int i = 1; i <= 10; i++) {
            pizzeria.placeOrder(new Order(i));
            Thread.sleep(100);
        }
        while (!pizzeria.isWorkCompleted()) {
            Thread.sleep(1000);
        }
        pizzeria.stop();
        assertTrue(pizzeria.isWorkCompleted());
    }

    @Test
    void testPizzeriaEmptyStorage() throws InterruptedException {
        int N = 1;
        int M = 1;
        int T = 1;
        int[] bakerSpeeds = {1};
        int[] courierCapacities = {1};
        int[] courierDeliveryTimes = {1};
        Pizzeria pizzeria = new Pizzeria(N, M, T, bakerSpeeds, courierCapacities, courierDeliveryTimes);
        pizzeria.start();
        pizzeria.placeOrder(new Order(1));
        Thread.sleep(3000);
        assertTrue(pizzeria.isWorkCompleted());
        pizzeria.stop();
    }
}