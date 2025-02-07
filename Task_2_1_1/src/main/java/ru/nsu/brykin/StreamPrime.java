package ru.nsu.brykin;

import java.util.Arrays;

public class StreamPrime implements PrimeChecker {
    @Override
    public boolean containsNonPrime(int[] numbers, int numTreads) {
        return Arrays.stream(numbers).parallel().anyMatch(n -> !PrimeUtils.isPrime(n));
    }
}