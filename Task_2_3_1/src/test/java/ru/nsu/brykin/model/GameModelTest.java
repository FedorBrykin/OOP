package ru.nsu.brykin.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GameModelTest {

    @Test
    void testCollision() {
        GameConfig config = new GameConfig();
        GameModel model = new GameModel(config);
        for (int i = 0; i < config.getWidth() + 1; i++) {
            model.update();
        }
        assertTrue(model.isGameOver());
    }
}