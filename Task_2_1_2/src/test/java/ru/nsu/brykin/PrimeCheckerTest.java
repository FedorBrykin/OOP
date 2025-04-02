package ru.nsu.brykin;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.util.concurrent.*;

public class PrimeCheckerTest {
    private static final int BASE_PORT = 12350;
    private static final int WORKERS_COUNT = 3;
    private List<PrimeCheckerWorker> workers = new ArrayList<>();
    private PrimeCheckerMaster master;

    @BeforeEach
    public void setUp() throws Exception {
        // Запускаем тестовых воркеров
        for (int i = 0; i < WORKERS_COUNT; i++) {
            int port = BASE_PORT + i;
            PrimeCheckerWorker worker = new PrimeCheckerWorker(port);
            worker.start();
            workers.add(worker);
        }

        // Создаем мастера
        List<String> workerAddresses = new ArrayList<>();
        for (int i = 0; i < WORKERS_COUNT; i++) {
            workerAddresses.add("127.0.0.1:" + (BASE_PORT + i));
        }
        master = new PrimeCheckerMaster(workerAddresses, BASE_PORT, 1000, 2);
    }

    @AfterEach
    public void tearDown() throws Exception {
        master.close();
        for (PrimeCheckerWorker worker : workers) {
            worker.close();
        }
        Thread.sleep(200);
    }

    @Test
    public void testAllPrimes() throws Exception {
        assertFalse(master.checkForComposite(Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19)),
                "все числа простые");
    }

    @Test
    public void testAllComposites() throws Exception {
        assertTrue(master.checkForComposite(List.of(4, 6, 8, 9, 10, 12, 14, 15)),
                "все числа составные");
    }

    @Test
    public void testMixedNumbers() throws Exception {
        assertTrue(master.checkForComposite(Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9)),
                "есть простые");
    }

    @Test
    public void testEmptyArray() throws Exception {
        assertFalse(master.checkForComposite(List.of()),
                "ничего");
    }

    @Test
    public void testSinglePrime() throws Exception {
        assertFalse(master.checkForComposite(List.of(13)),
                "простое");
    }

    @Test
    public void testSingleComposite() throws Exception {
        assertTrue(master.checkForComposite(List.of(15)),
                "нет");
    }

    @Test
    public void testConcurrentRequests() throws Exception {
        int threadCount = 5;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        List<Future<Boolean>> futures = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {
            List<Integer> numbers = Arrays.asList(12 + i, 13 + i, 14 + i, 15 + i);
            futures.add(executor.submit(() -> master.checkForComposite(numbers)));
        }

        for (Future<Boolean> future : futures) {
            assertNotNull(future.get(2, TimeUnit.SECONDS));
        }

        executor.shutdown();
    }

    @Test
    public void testWorkerFailure() throws Exception {
        workers.getFirst().close();
        Thread.sleep(500);
        boolean result = master.checkForComposite(Arrays.asList(2, 3, 4, 5));
        assertTrue(result, "Система должна продолжать работать");
    }
}