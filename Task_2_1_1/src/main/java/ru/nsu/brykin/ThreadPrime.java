package ru.nsu.brykin;

import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadPrime {
    private static final AtomicBoolean result = new AtomicBoolean(false);

    public static boolean containsNonPrime(int[] numbers, int numThreads) throws InterruptedException {
        int threadTask = (numbers.length + numThreads - 1) / numThreads;
        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            int start = i * threadTask;
            int end = Math.min(start + threadTask, numbers.length);
            threads[i] = new Thread(new PrimeCheckTask(numbers, start, end));
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        return result.get();
    }

    private record PrimeCheckTask(int[] numbers, int start, int end) implements Runnable {

        @Override
            public void run() {
                for (int i = start; i < end; i++) {
                    if (!isPrime(numbers[i])) {
                        result.set(true);
                        return;
                    }
                }
            }
        }

    private static boolean isPrime(int number) {
        if (number < 2) {
            return false;
        }
        for (int i = 2; i * i <= number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}