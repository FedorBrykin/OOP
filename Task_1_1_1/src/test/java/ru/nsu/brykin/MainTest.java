package ru.nsu.brykin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr1);

        int[] arr2 = {1, 2, 3, 4, 5};
        Main.sort(arr2);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr2);

        int[] arr4 = {};
        Main.sort(arr4);
        assertArrayEquals(new int[]{}, arr4);
    }
}