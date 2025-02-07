package ru.nsu.brykin;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StreamPrimeTest {

    PrimeChecker checker = new StreamPrime();

    @Test
    void testContainsNonPrimeWithNonPrimeNumbers() throws InterruptedException {
        int[] nonPrimes = {4, 6, 8, 9, 10};
        assertTrue(checker.containsNonPrime(nonPrimes, 1000));
    }

    @Test
    void testContainsNonPrimeWithAllPrimeNumbers() throws InterruptedException {
        int[] primes = {2, 3, 5, 7, 11};
        assertFalse(checker.containsNonPrime(primes, 1000));
    }

    @Test
    void testContainsNonPrimeWithEmptyArray() throws InterruptedException {
        int[] empty = {};
        assertFalse(checker.containsNonPrime(empty, 1000));
    }

    @Test
    void testContainsNonPrimeWithSinglePrimeNumber() throws InterruptedException {
        int[] singlePrime = {13};
        assertFalse(checker.containsNonPrime(singlePrime, 1000));

    }

    @Test
    void testContainsNonPrimeWithSingleNonPrimeNumber() throws InterruptedException {
        int[] singleNonPrime = {15};
        assertTrue(checker.containsNonPrime(singleNonPrime, 1000));
    }
}