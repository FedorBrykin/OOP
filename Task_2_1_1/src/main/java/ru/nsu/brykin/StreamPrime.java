package ru.nsu.brykin;

import java.util.Arrays;

/**
 * streams.
 */
public class StreamPrime implements PrimeChecker {
    @Override
    public boolean containsNonPrime(int[] numbers) {
        return Arrays.stream(numbers).parallel().anyMatch(n -> !PrimeUtils.isPrime(n));
    }
}