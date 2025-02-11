package ru.nsu.brykin;

/**
 * Тестовые значения.
 */
public class PerformanceTest {
    /**
     * Запуск.
     */
    public static void main(String[] args) throws InterruptedException {
        int[] largePrimes = PrimeGen.generateLargePrimeArray(1000000);

        // Последовательный алгоритм
        runTest(new SeqPrime(), "Sequential", largePrimes);

        // 2 потока
        runTest(new ThreadPrime(2), "Parallel Thread2", largePrimes);

        // 3 потока
        runTest(new ThreadPrime(3), "Parallel Thread3", largePrimes);

        // 4 потока
        runTest(new ThreadPrime(4), "Parallel Thread4", largePrimes);

        // parallelStream
        runTest(new StreamPrime(), "Parallel Stream", largePrimes);
    }

    /**
     * решение проблемы с дублированием.
     */
    private static void runTest(PrimeChecker checker, String testName, int[] largePrimes)
            throws InterruptedException {
        long startTime = System.nanoTime();
        boolean result = checker.containsNonPrime(largePrimes);
        long endTime = System.nanoTime();
        System.out.println(testName + " result: " + result);
        System.out.println(testName + " execution time: "
                + (endTime - startTime) / 1000000 + " ms");
        System.out.println(' ');
    }
}
