package ru.nsu.brykin;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PizzeriaTest {
    @Test
    void testPizzeria() throws InterruptedException {
        int N = 2;
        int M = 2;
        int T = 5;
        int[] bakerSpeeds = {1, 2};
        int[] courierCapacities = {1, 2};
        int[] deliveryTimes = {1, 2};
        Pizzeria pizzeria = new Pizzeria(N, M, T, bakerSpeeds, courierCapacities, deliveryTimes);
        pizzeria.start();
        for (int i = 1; i <= 5; i++) {
            pizzeria.placeOrder(new Order(i));
            Thread.sleep(500);
        }

        while (!pizzeria.isWorkCompleted()) {
            Thread.sleep(1000);
        }
        pizzeria.stop();
        assertTrue(pizzeria.isWorkCompleted());
    }
}