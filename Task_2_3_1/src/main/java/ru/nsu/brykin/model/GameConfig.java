package ru.nsu.brykin.model;

/**
 * подготовка к игре.
 */
public class GameConfig {
    private int width = 20;
    private int height = 20;
    private int initialSnakeLength = 1;
    private int winLength = 10;

    /**
     * широта.
     */
    public int getWidth() {
        return width;
    }

    /**
     * долгота.
     */
    public int getHeight() {
        return height;
    }

    /**
     * InitialSnakeLength.
     */
    public int getInitialSnakeLength() {
        return initialSnakeLength;
    }

    /**
     * WinLength.
     */
    public int getWinLength() {
        return winLength;
    }
}