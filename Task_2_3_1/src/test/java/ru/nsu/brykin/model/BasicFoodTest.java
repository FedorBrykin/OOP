package ru.nsu.brykin.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * тестики еды.
 */
class BasicFoodTest {

    @Test
    void testSpawn() {
        BasicFood food = new BasicFood();
        Snake snake = new BasicSnake(5, 5, 3);
        List<Point> snakeBody = snake.getBody();

        food.spawn(10, 10, snakeBody);
        assertFalse(snakeBody.contains(new Point(food.getX(), food.getY())));
    }

    @Test
    void testGetScore() {
        BasicFood food = new BasicFood();
        assertEquals(1, food.getScore());
    }
}