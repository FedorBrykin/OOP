package ru.nsu.brykin.model;


public class GameModel {
    private Snake snake;
    private Food food;
    private GameConfig config;
    private boolean isGameOver;
    private boolean isWin;

    public GameModel(GameConfig config) {
        this.config = config;
        snake = new BasicSnake(config.getWidth() / 2, config.getHeight() / 2, config.getInitialSnakeLength());
        food = new BasicFood();
        food.spawn(config.getWidth(), config.getHeight(), snake.getBody());
    }

    public void update() {
        if (isGameOver) {
            return;
        }

        snake.move();
        checkCollision();
        checkFood();
        checkWin();
    }

    private void checkCollision() {
        Point head = snake.getHead();
        if (head.x < 0 || head.x >= config.getWidth() || head.y < 0 || head.y >= config.getHeight()) {
            gameOver();
            return;
        }

        for (Point bodyPart : snake.getBody()) {
            if (bodyPart.x == head.x && bodyPart.y == head.y && bodyPart != head) {
                gameOver();
                return;
            }
        }
    }

    private void checkFood() {
        if (snake.getHead().x == food.getX() && snake.getHead().y == food.getY()) {
            snake.grow();
            food.spawn(config.getWidth(), config.getHeight(), snake.getBody());
        }
    }

    private void checkWin() {
        if (snake.getBody().size() >= config.getWinLength()) {
            isWin = true;
            gameOver();
        }
    }

    private void gameOver() {
        isGameOver = true;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public boolean isWin() {
        return isWin;
    }

    public Snake getSnake() {
        return snake;
    }

    public Food getFood() {
        return food;
    }

    public GameConfig getConfig() {
        return config;
    }
}