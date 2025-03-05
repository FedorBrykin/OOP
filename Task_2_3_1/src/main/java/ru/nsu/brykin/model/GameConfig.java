package ru.nsu.brykin.model;

public class GameConfig {
    private int width = 20;
    private int height = 20;
    private int initialSnakeLength = 1;
    private int winLength = 10;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getInitialSnakeLength() {
        return initialSnakeLength;
    }

    public int getWinLength() {
        return winLength;
    }
}