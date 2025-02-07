package ru.nsu.brykin;

import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadPrime implements PrimeChecker {
    @Override
    public boolean containsNonPrime(int[] numbers, int numThreads) throws InterruptedException {
        AtomicBoolean result = new AtomicBoolean(false);
        int chunkSize = (numbers.length + numThreads - 1) / numThreads;
        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, numbers.length);
            threads[i] = new Thread(new PrimeCheckTask(numbers, start, end, result));
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        return result.get();
    }

    private record PrimeCheckTask(int[] numbers, int start, int end, AtomicBoolean result) implements Runnable {
        @Override
        public void run() {
            for (int i = start; i < end; i++) {
                if (!PrimeUtils.isPrime(numbers[i])) {
                    result.set(true);
                    return;
                }
            }
        }
    }
}