package ru.nsu.brykin.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameConfigTest {

    @Test
    void testGetWidth() {
        GameConfig config = new GameConfig();
        assertEquals(20, config.getWidth());
    }

    @Test
    void testGetHeight() {
        GameConfig config = new GameConfig();
        assertEquals(20, config.getHeight());
    }

    @Test
    void testGetInitialSnakeLength() {
        GameConfig config = new GameConfig();
        assertEquals(1, config.getInitialSnakeLength());
    }

    @Test
    void testGetWinLength() {
        GameConfig config = new GameConfig();
        assertEquals(10, config.getWinLength());
    }
}