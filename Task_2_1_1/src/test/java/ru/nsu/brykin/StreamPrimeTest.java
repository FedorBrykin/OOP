package ru.nsu.brykin;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * тесты для стримов.
 */
class StreamPrimeTest {

    PrimeChecker checker = new StreamPrime();

    @Test
    void testContainsNonPrimeWithNonPrimeNumbers() throws InterruptedException {
        int[] nonPrimes = {4, 6, 8, 9, 10};
        assertTrue(checker.containsNonPrime(nonPrimes));
    }

    @Test
    void testContainsNonPrimeWithAllPrimeNumbers() throws InterruptedException {
        int[] primes = {2, 3, 5, 7, 11};
        assertFalse(checker.containsNonPrime(primes));
    }

    @Test
    void testContainsNonPrimeWithEmptyArray() throws InterruptedException {
        int[] empty = {};
        assertFalse(checker.containsNonPrime(empty));
    }

    @Test
    void testContainsNonPrimeWithSinglePrimeNumber() throws InterruptedException {
        int[] singlePrime = {13};
        assertFalse(checker.containsNonPrime(singlePrime));

    }

    @Test
    void testContainsNonPrimeWithSingleNonPrimeNumber() throws InterruptedException {
        int[] singleNonPrime = {15};
        assertTrue(checker.containsNonPrime(singleNonPrime));
    }
}