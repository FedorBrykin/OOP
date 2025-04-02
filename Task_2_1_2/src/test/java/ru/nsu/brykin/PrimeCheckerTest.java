package ru.nsu.brykin;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import java.util.concurrent.*;

public class PrimeCheckerTest {
    private static final int BASE_PORT = 12350;
    private static final int WORKERS_COUNT = 3;
    private List<PrimeCheckerWorker> workers = new ArrayList<>();
    private PrimeCheckerMaster master;

    @Before
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

    @After
    public void tearDown() throws Exception {
        master.close();
        for (PrimeCheckerWorker worker : workers) {
            worker.close();
        }
        Thread.sleep(200);
    }

    @Test
    public void testAllPrimes() throws Exception {
        List<Integer> primes = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19);
        boolean result = master.checkForComposite(primes);
        assertFalse("Ожидалось, что все числа простые", result);
    }

    @Test
    public void testAllComposites() throws Exception {
        List<Integer> composites = Arrays.asList(4, 6, 8, 9, 10, 12, 14, 15);
        boolean result = master.checkForComposite(composites);
        assertTrue("Ожидалось, что все числа составные", result);
    }

    @Test
    public void testMixedNumbers() throws Exception {
        List<Integer> mixed = Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9);
        boolean result = master.checkForComposite(mixed);
        assertTrue("Ожидалось наличие составных чисел", result);
    }

    @Test
    public void testEmptyArray() throws Exception {
        boolean result = master.checkForComposite(Collections.emptyList());
        assertFalse("Пустой массив не должен содержать составных чисел", result);
    }

    @Test(timeout = 5000)
    public void testSinglePrime() throws Exception {
        boolean result = master.checkForComposite(Collections.singletonList(13));
        assertFalse("13 - простое число", result);
    }

    @Test(timeout = 5000)
    public void testSingleComposite() throws Exception {
        boolean result = master.checkForComposite(Collections.singletonList(15));
        assertTrue("15 - составное число", result);
    }

    @Test(timeout = 10000)
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

    @Test(timeout = 5000)
    public void testWorkerFailure() throws Exception {
        workers.get(0).close();
        Thread.sleep(500);

        boolean result = master.checkForComposite(Arrays.asList(2, 3, 4, 5));
        assertTrue("Система должна продолжать работать", result);
    }
}