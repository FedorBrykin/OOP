package ru.nsu.brykin;


public class Main {
    public static void main(String[] args) throws Exception {
        Pizzeria pizzeria = new Pizzeria(2, 2, 5, new int[] {1, 2}, new int[] {1, 2});
        pizzeria.start();
        boolean isOpen = true;

        // Время работы пиццерии (10 секунд)
        long workTime = 10000; // 10 секунд
        long startTime = System.currentTimeMillis();

        // Генерация заказов
        int orderId = 1;
        while (isOpen) {
            if (System.currentTimeMillis() - startTime >= workTime) {
                isOpen = false;
                System.out.println("Pizzeria closed.");
                break;
            }
            Order order = new Order(orderId);
            pizzeria.placeOrder(order);

            // Увеличиваем ID заказа
            orderId++;

            // Задержка между заказами (например, 1 секунда)
            Thread.sleep(1000);
        }

        // Ждем завершения всех заказов
        while (!pizzeria.isWorkCompleted()) {
            System.out.println("waiting...");
            Thread.sleep(1000); // Проверяем каждую секунду
        }

        // Завершаем работу пиццерии
        pizzeria.stop();
        System.out.println("Thats all.");
    }
}