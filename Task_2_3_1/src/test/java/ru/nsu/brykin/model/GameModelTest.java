package ru.nsu.brykin.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {

    private GameModel gameModel;

    @BeforeEach
    void setUp() {
        gameModel = new GameModel(20, 20, 1);
    }

    @Test
    void testInitialState() {
        assertFalse(gameModel.isGameOver());
        assertNotNull(gameModel.getSnake());
        assertNotNull(gameModel.getFood());
    }

    @Test
    void testSnakeEatsFood() {
        gameModel.getFood().x = gameModel.getSnake().getHead().x + 1;
        gameModel.getFood().y = gameModel.getSnake().getHead().y;

        int initialLength = gameModel.getSnake().getBody().size();
        gameModel.update();
        assertEquals(initialLength + 1, gameModel.getSnake().getBody().size());
    }

    @Test
    void testGameOverOnWallCollision() {
        gameModel.getSnake().setDirection(SnakeModel.Direction.LEFT);
        for (int i = 0; i < 20; i++) {
            gameModel.update();
        }
        assertTrue(gameModel.isGameOver());
    }
}