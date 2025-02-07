package ru.nsu.brykin;

public class PerformanceTest {
    public static void main(String[] args) throws InterruptedException {
        int[] largePrimes = PrimeGen.generateLargePrimeArray(1000000);

        // Последовательный алгоритм
        PrimeChecker checker1 = new SeqPrime();
        long startTime = System.nanoTime();
        boolean sequentialResult = checker1.containsNonPrime(largePrimes, 1);
        long endTime = System.nanoTime();
        System.out.println("Sequential result: " + sequentialResult);
        System.out.println("Sequential execution time: " + (endTime - startTime) / 1000000 + " ms");
        System.out.println(' ');

        // 2 потока
        PrimeChecker checker2 = new ThreadPrime();
        startTime = System.nanoTime();
        boolean parallelThreadResult2 = checker2.containsNonPrime(largePrimes, 2);
        endTime = System.nanoTime();
        System.out.println("Parallel Thread2 result: " + parallelThreadResult2);
        System.out.println("Parallel Thread2 execution time: " + (endTime - startTime) / 1000000 + " ms");
        System.out.println(' ');

        // 3 потока
        PrimeChecker checker3 = new ThreadPrime();
        startTime = System.nanoTime();
        boolean parallelThreadResult3 = checker3.containsNonPrime(largePrimes, 3);
        endTime = System.nanoTime();
        System.out.println("Parallel Thread3 result: " + parallelThreadResult3);
        System.out.println("Parallel Thread3 execution time: " + (endTime - startTime) / 1000000 + " ms");
        System.out.println(' ');

        // 4 потока
        PrimeChecker checker4 = new ThreadPrime();
        startTime = System.nanoTime();
        boolean parallelThreadResult4 = checker4.containsNonPrime(largePrimes, 4);
        endTime = System.nanoTime();
        System.out.println("Parallel Thread4 result: " + parallelThreadResult4);
        System.out.println("Parallel Thread4 execution time: " + (endTime - startTime) / 1000000 + " ms");
        System.out.println(' ');

        // parallelStream
        PrimeChecker checker5 = new StreamPrime();
        startTime = System.nanoTime();
        boolean parallelStreamResult = checker5.containsNonPrime(largePrimes, 1000000);
        endTime = System.nanoTime();
        System.out.println("Parallel Stream result: " + parallelStreamResult);
        System.out.println("Parallel Stream execution time: " + (endTime - startTime) / 1000000 + " ms");
    }
}