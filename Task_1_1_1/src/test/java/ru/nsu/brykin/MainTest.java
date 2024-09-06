package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class MainTest {
    @Test
    void checkMain(){
        Main.main(null);
        assertTrue(true);
    }
    @Test
    public void testSort() {
        int[] arr1 = {5, 4, 3, 2, 1};
        Main.sort(arr1);
        assertArrayEquals(new int[] {1, 2, 3, 4, 5}, arr1);

        int[] arr2 = {1, 2, 3, 4, 5};
        Main.sort(arr2);
        assertArrayEquals(new int[] {1, 2, 3, 4, 5}, arr2);

        int[] arr3 = {};
        Main.sort(arr3);
        assertArrayEquals(new int[] {}, arr3);
    }
}