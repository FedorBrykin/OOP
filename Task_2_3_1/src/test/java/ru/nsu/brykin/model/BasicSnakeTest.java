package ru.nsu.brykin.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BasicSnakeTest {

    @Test
    void testMove() {
        BasicSnake snake = new BasicSnake(5, 5, 3);
        snake.setDirection(Direction.RIGHT);
        snake.move();
        assertEquals(6, snake.getHead().x);
        assertEquals(5, snake.getHead().y);
        assertEquals(3, snake.getBody().size());
    }

    @Test
    void testGrow() {
        BasicSnake snake = new BasicSnake(5, 5, 3);
        snake.grow();
        assertEquals(4, snake.getBody().size());
    }
}