package ru.nsu.brykin.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FoodModelTest {

    private FoodModel food;

    @BeforeEach
    void setUp() {
        food = new FoodModel(20, 20);
    }

    @Test
    void testSpawn() {
        food.spawn(20, 20);
        assertTrue(food.x >= 0 && food.x < 20);
        assertTrue(food.y >= 0 && food.y < 20);
    }
}