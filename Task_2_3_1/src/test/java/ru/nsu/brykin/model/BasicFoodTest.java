package ru.nsu.brykin.model;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

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