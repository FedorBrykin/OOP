package ru.nsu.brykin;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ThreadPrimeTest {
    PrimeChecker checker = new ThreadPrime();

    @Test
    void testContainsNonPrimeWithNonPrimeNumbers() throws InterruptedException {
        int[] nonPrimes = {4, 6, 8, 9, 10};
        assertTrue(checker.containsNonPrime(nonPrimes, 2));
    }

    @Test
    void testContainsNonPrimeWithAllPrimeNumbers() throws InterruptedException {
        int[] primes = {2, 3, 5, 7, 11};
        assertFalse(checker.containsNonPrime(primes, 3));
    }

    @Test
    void testContainsNonPrimeWithEmptyArray() throws InterruptedException {
        int[] empty = {};
        assertFalse(checker.containsNonPrime(empty, 4));
    }

    @Test
    void testContainsNonPrimeWithSinglePrimeNumber() throws InterruptedException {
        int[] singlePrime = {13};
        assertFalse(checker.containsNonPrime(singlePrime, 3));

    }

    @Test
    void testContainsNonPrimeWithSingleNonPrimeNumber() throws InterruptedException {
        int[] singleNonPrime = {15};
        assertTrue(checker.containsNonPrime(singleNonPrime, 2));
    }
}