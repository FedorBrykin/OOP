package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 * Тестики.
 */
public class PrimeCheckerTest {
    private static final int TEST_PORT = 8080;
    private PrimeCheckerWorker worker;
    private Thread workerThread;

    @BeforeEach
    void startWorker() throws IOException {
        worker = new PrimeCheckerWorker();
        workerThread = new Thread(() -> {
            try {
                worker.startServer(TEST_PORT);
            } catch (IOException e) {
                System.err.println("Failed to start worker server: " + e.getMessage());
                throw new RuntimeException("Worker server startup failed", e);
            }
        });
        workerThread.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Worker thread interrupted during startup", e);
        }
    }

    @AfterEach
    void stopWorker() {
        worker.stopServer();
        workerThread.interrupt();
    }

    @Test
    @Timeout(5)
    void testSinglePrimeBatch() throws Exception {
        try (Socket socket = new Socket("localhost", TEST_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            List<Integer> primes = Arrays.asList(2, 3, 5, 7, 11);
            out.writeObject(primes);
            out.flush();

            boolean result = (Boolean) in.readObject();
            assertFalse(result, "Batch should not contain composite numbers");
        }
    }

    @Test
    @Timeout(5)
    void testSingleCompositeBatch() throws Exception {
        try (Socket socket = new Socket("localhost", TEST_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            List<Integer> composites = Arrays.asList(4, 6, 8, 9, 10);
            out.writeObject(composites);
            out.flush();

            boolean result = (Boolean) in.readObject();
            assertTrue(result, "Batch should contain composite numbers");
        }
    }

    @Test
    @Timeout(5)
    void testMixedBatch() throws Exception {
        try (Socket socket = new Socket("localhost", TEST_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            List<Integer> mixed = Arrays.asList(2, 3, 4, 5, 6);
            out.writeObject(mixed);
            out.flush();

            boolean result = (Boolean) in.readObject();
            assertTrue(result, "Mixed batch should contain composite numbers");
        }
    }

    @Test
    @Timeout(5)
    void testConcurrentRequests() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Future<Boolean>> futures = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            futures.add(executor.submit(() -> {
                try (Socket socket = new Socket("localhost", TEST_PORT);
                     ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                     ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                    List<Integer> numbers = Arrays.asList(12, 13, 14, 15);
                    out.writeObject(numbers);
                    out.flush();
                    return (Boolean) in.readObject();
                }
            }));
        }

        for (Future<Boolean> future : futures) {
            assertTrue(future.get(2, TimeUnit.SECONDS));
        }

        executor.shutdown();
    }

    @Test
    @Timeout(5)
    void testEmptyBatch() throws Exception {
        try (Socket socket = new Socket("localhost", TEST_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            List<Integer> empty = Collections.emptyList();
            out.writeObject(empty);
            out.flush();

            boolean result = (Boolean) in.readObject();
            assertFalse(result, "Empty batch should return false");
        }
    }

    @Test
    @Timeout(5)
    void testLargePrimeNumber() throws Exception {
        try (Socket socket = new Socket("localhost", TEST_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            List<Integer> largePrime = Collections.singletonList(104729); // 10000th prime
            out.writeObject(largePrime);
            out.flush();

            boolean result = (Boolean) in.readObject();
            assertFalse(result, "Large prime number should return false");
        }
    }
}