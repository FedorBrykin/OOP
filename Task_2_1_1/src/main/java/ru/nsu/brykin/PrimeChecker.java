package ru.nsu.brykin;

public interface PrimeChecker {
    boolean containsNonPrime(int[] numbers, int numTreads) throws InterruptedException;
}