package ru.nsu.brykin;

public class PerformanceTest {
    public static void main(String[] args) throws InterruptedException {
        int[] largePrimes = PrimeGen.generateLargePrimeArray(1000000);

        // Последовательный алгоритм
        long startTime = System.nanoTime();
        boolean sequentialResult = SeqPrime.containsNonPrime(largePrimes);
        long endTime = System.nanoTime();
        System.out.println("Sequential result: " + sequentialResult);
        System.out.println("Sequential execution time: " + (endTime - startTime) / 1000000 + " ms");
        System.out.println(' ');

        // 2 потока
        startTime = System.nanoTime();
        boolean parallelThreadResult2 = ThreadPrime.containsNonPrime(largePrimes, 2);
        endTime = System.nanoTime();
        System.out.println("Parallel Thread2 result: " + parallelThreadResult2);
        System.out.println("Parallel Thread2 execution time: " + (endTime - startTime) / 1000000 + " ms");
        System.out.println(' ');

        // 3 потока
        startTime = System.nanoTime();
        boolean parallelThreadResult3 = ThreadPrime.containsNonPrime(largePrimes, 3);
        endTime = System.nanoTime();
        System.out.println("Parallel Thread3 result: " + parallelThreadResult3);
        System.out.println("Parallel Thread3 execution time: " + (endTime - startTime) / 1000000 + " ms");
        System.out.println(' ');

        // 4 потока
        startTime = System.nanoTime();
        boolean parallelThreadResult4 = ThreadPrime.containsNonPrime(largePrimes, 4);
        endTime = System.nanoTime();
        System.out.println("Parallel Thread4 result: " + parallelThreadResult4);
        System.out.println("Parallel Thread4 execution time: " + (endTime - startTime) / 1000000 + " ms");
        System.out.println(' ');

        // parallelStream
        startTime = System.nanoTime();
        boolean parallelStreamResult = StreamPrime.containsNonPrime(largePrimes);
        endTime = System.nanoTime();
        System.out.println("Parallel Stream result: " + parallelStreamResult);
        System.out.println("Parallel Stream execution time: " + (endTime - startTime) / 1000000 + " ms");
    }
}