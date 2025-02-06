package ru.nsu.brykin;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StreamPrimeTest {

    @Test
    void testContainsNonPrimeWithNonPrimeNumbers() {
        int[] numbers = {4, 6, 8, 9, 10};
        assertTrue(StreamPrime.containsNonPrime(numbers));
    }

    @Test
    void testContainsNonPrimeWithAllPrimeNumbers() {
        int[] numbers = {2, 3, 5, 7, 11};
        assertFalse(StreamPrime.containsNonPrime(numbers));
    }

    @Test
    void testContainsNonPrimeWithSinglePrimeNumber() {
        int[] numbers = {13};
        assertFalse(StreamPrime.containsNonPrime(numbers));
    }

    @Test
    void testContainsNonPrimeWithSingleNonPrimeNumber() {
        int[] numbers = {15};
        assertTrue(StreamPrime.containsNonPrime(numbers));
    }
}