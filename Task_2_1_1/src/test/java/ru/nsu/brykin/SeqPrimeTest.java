package ru.nsu.brykin;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SeqPrimeTest {

    @Test
    void testContainsNonPrimeWithNonPrimeNumbers() {
        int[] numbers = {4, 6, 8, 9, 10};
        assertTrue(SeqPrime.containsNonPrime(numbers));
    }

    @Test
    void testContainsNonPrimeWithAllPrimeNumbers() {
        int[] numbers = {2, 3, 5, 7, 11};
        assertFalse(SeqPrime.containsNonPrime(numbers));
    }

    @Test
    void testContainsNonPrimeWithEmptyArray() {
        int[] numbers = {};
        assertFalse(SeqPrime.containsNonPrime(numbers));
    }

    @Test
    void testContainsNonPrimeWithSinglePrimeNumber() {
        int[] numbers = {13};
        assertFalse(SeqPrime.containsNonPrime(numbers));
    }

    @Test
    void testContainsNonPrimeWithSingleNonPrimeNumber() {
        int[] numbers = {15};
        assertTrue(SeqPrime.containsNonPrime(numbers));
    }
}