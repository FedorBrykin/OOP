package ru.nsu.brykin;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * тесты для потоков.
 */
class ThreadPrimeTest {

    @Test
    void testContainsNonPrimeWithNonPrimeNumbers() throws InterruptedException {
        PrimeChecker checker = new ThreadPrime(2);
        int[] nonPrimes = {4, 6, 8, 9, 10};
        assertTrue(checker.containsNonPrime(nonPrimes));
    }

    @Test
    void testContainsNonPrimeWithAllPrimeNumbers() throws InterruptedException {
        PrimeChecker checker = new ThreadPrime(3);
        int[] primes = {2, 3, 5, 7, 11};
        assertFalse(checker.containsNonPrime(primes));
    }

    @Test
    void testContainsNonPrimeWithEmptyArray() throws InterruptedException {
        PrimeChecker checker = new ThreadPrime(4);
        int[] empty = {};
        assertFalse(checker.containsNonPrime(empty));
    }

    @Test
    void testContainsNonPrimeWithSinglePrimeNumber() throws InterruptedException {
        PrimeChecker checker = new ThreadPrime(3);
        int[] singlePrime = {13};
        assertFalse(checker.containsNonPrime(singlePrime));

    }

    @Test
    void testContainsNonPrimeWithSingleNonPrimeNumber() throws InterruptedException {
        PrimeChecker checker = new ThreadPrime(2);
        int[] singleNonPrime = {15};
        assertTrue(checker.containsNonPrime(singleNonPrime));
    }
}