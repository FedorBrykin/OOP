package ru.nsu.brykin.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SnakeModelTest {

    private SnakeModel snake;

    @BeforeEach
    void setUp() {
        snake = new SnakeModel(10, 10);
    }

    @Test
    void testInitialLength() {
        assertEquals(1, snake.getBody().size());
    }

    @Test
    void testMove() {
        snake.setDirection(SnakeModel.Direction.RIGHT);
        snake.move();
        assertEquals(11, snake.getHead().x);
        assertEquals(10, snake.getHead().y);
    }

    @Test
    void testGrow() {
        int initialSize = snake.getBody().size();
        snake.grow();
        assertEquals(initialSize + 1, snake.getBody().size());
    }
}