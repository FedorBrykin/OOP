package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;



public class PrimeCheckerIntegrationTest {
    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void testMasterWorkerIntegration() throws Exception {
        PrimeCheckerWorker worker = new PrimeCheckerWorker();
        Thread workerThread = new Thread(() -> {
            try {
                worker.start();
            } catch (IOException e) {
                fail("Worker failed to start");
            }
        });
        workerThread.start();

        Thread.sleep(1000);

        PrimeCheckerMaster master = new PrimeCheckerMaster();
        master.processNumbers("test_numbers.txt");
        master.awaitCompletion();

        assertFalse(master.hasCompositeNumbers());
        worker.stopServer();
        workerThread.interrupt();
    }
}