package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class MainTest {
    @Test
    void checkMain() {
        Heapsort.main(null);
        assertTrue(true);
    }

    @Test
    public void testReversed() {
        int[] arr = {5, 4, 3, 2, 1};
        Heapsort.sort(arr);
        assertArrayEquals(new int[] {1, 2, 3, 4, 5}, arr);
    }

    @Test
    public void testCorrectOrder() {
        int[] arr = {1, 2, 3, 4, 5};
        Heapsort.sort(arr);
        assertArrayEquals(new int[] {1, 2, 3, 4, 5}, arr);
    }

    @Test
    public void testEmpty() {
        int[] arr = {};
        Heapsort.sort(arr);
        assertArrayEquals(new int[] {}, arr);
    }

    @Test
    void testSingleElem() {
        int[] arr = {0};
        Heapsort.sort(arr);
        assertArrayEquals(new int[] {0}, arr);
    }

    @Test
    public void testRandomOrder() {
        int[] arr = {5, 2, 4, 1, 3};
        Heapsort.sort(arr);
        assertArrayEquals(new int[] {1, 2, 3, 4, 5}, arr);
    }
}