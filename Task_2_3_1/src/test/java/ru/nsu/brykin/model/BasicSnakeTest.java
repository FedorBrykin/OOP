package ru.nsu.brykin.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * тестим змею.
 */
class BasicSnakeTest {

    @Test
    void testMove() {
        BasicSnake snake = new BasicSnake(5, 5, 3);
        snake.setDirection(Direction.RIGHT);
        snake.move();
        assertEquals(6, snake.getHead().posX);
        assertEquals(5, snake.getHead().posY);
        assertEquals(3, snake.getBody().size());
    }

    @Test
    void testGrow() {
        BasicSnake snake = new BasicSnake(5, 5, 3);
        snake.grow();
        assertEquals(4, snake.getBody().size());
    }
}
