package ru.nsu.brykin;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ThreadPrimeTest {

    @Test
    void testContainsNonPrimeWithNonPrimeNumbers() throws InterruptedException {
        int[] numbers = {4, 6, 8, 9, 10};
        assertTrue(ThreadPrime.containsNonPrime(numbers, 2));
    }

    @Test
    void testContainsNonPrimeWithAllPrimeNumbers() throws InterruptedException {
        int[] numbers = {2, 3, 5, 7};
        assertFalse(ThreadPrime.containsNonPrime(numbers, 2));
    }

    @Test
    void testContainsNonPrimeWithSingleNonPrimeNumber() throws InterruptedException {
        int[] numbers = {15};
        assertTrue(ThreadPrime.containsNonPrime(numbers, 2));
    }
}