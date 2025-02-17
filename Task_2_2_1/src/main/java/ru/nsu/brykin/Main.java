package ru.nsu.brykin;


import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * main.
 */
public class Main {
    /**
     * main.
     */
    public static void main(String[] args) throws Exception {
        Config config = loadConfig("config.json");
        Pizzeria pizzeria = createPizzeria(config);
        runPizzeria(pizzeria, 10, 1000);
    }

    /**
     * берём данные из конфига.
     */
    private static Config loadConfig(String filename) throws Exception {
        Gson gson = new Gson();
        try (InputStream input = Main.class.getResourceAsStream("/" + filename)) {
            if (input == null) {
                throw new FileNotFoundException("Файл " + filename + " не найден в classpath");
            }
            return gson.fromJson(new InputStreamReader(input), Config.class);
        }
    }


    /**
     * открываем пиццерию.
     */
    private static Pizzeria createPizzeria(Config config) {
        return new Pizzeria(
                config.getN(),
                config.getM(),
                config.getT(),
                config.getBakerSpeeds(),
                config.getCourierCapacities(),
                config.getCourierDeliveryTimes()
        );
    }

    /**
     * запуск пиццерии.
     */
    private static void runPizzeria(Pizzeria pizzeria, int workTimeSeconds, int orderDelayMillis) throws InterruptedException {
        pizzeria.start();

        long startTime = System.currentTimeMillis();
        int orderId = 1;

        while (System.currentTimeMillis() - startTime < workTimeSeconds * 1000L) {
            pizzeria.placeOrder(new Order(orderId));
            System.out.println("[" + orderId + "] [заказ принят]");
            orderId++;
            Thread.sleep(orderDelayMillis);
        }

        System.out.println("Пиццерия закрыта для новых заказов.");

        while (!pizzeria.isWorkCompleted()) {
            System.out.println("Ожидание завершения оставшихся заказов...");
            Thread.sleep(1000);
        }

        pizzeria.stop();
        System.out.println("Все заказы завершены. Пиццерия завершила работу.");
    }
}