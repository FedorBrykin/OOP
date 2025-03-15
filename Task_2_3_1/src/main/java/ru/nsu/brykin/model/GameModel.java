package ru.nsu.brykin.model;


/**
 * В игру Джон, в игру!.
 */
public class GameModel {
    private Snake snake;
    private Food food;
    private GameConfig config;
    private boolean isGameOver;
    private boolean isWin;

    /**
     * В игру Джон, в игру!).
     */
    public GameModel(GameConfig config) {
        this.config = config;
        snake = new BasicSnake(config.getWidth() / 2, config.getHeight() / 2,
                config.getInitialSnakeLength());
        food = new BasicFood();
        food.spawn(config.getWidth(), config.getHeight(), snake.getBody());
    }

    /**
     * релиз не ждёт.
     */
    public void update() {
        if (isGameOver) {
            return;
        }

        snake.move();
        checkCollision();
        checkFood();
        checkWin();
    }

    /**
     * Титаник должен видеть айсберг!.
     */
    private void checkCollision() {
        Point head = snake.getHead();
        if (head.posX < 0 || head.posX >= config.getWidth() || head.posY < 0
                || head.posY >= config.getHeight()) {
            gameOver();
            return;
        }

        for (Point bodyPart : snake.getBody()) {
            if (bodyPart.posX == head.posX && bodyPart.posY == head.posY && bodyPart != head) {
                gameOver();
                return;
            }
        }
    }

    /**
     * есть поесть?.
     */
    private void checkFood() {
        if (snake.getHead().posX == food.getX() && snake.getHead().posY == food.getY()) {
            snake.grow();
            food.spawn(config.getWidth(), config.getHeight(), snake.getBody());
        }
    }

    /**
     * победа.
     */
    private void checkWin() {
        if (snake.getBody().size() >= config.getWinLength()) {
            isWin = true;
            gameOver();
        }
    }

    /**
     * увы, на этом всё(.
     */
    private void gameOver() {
        isGameOver = true;
    }

    /**
     * или нет?.
     */
    public boolean isGameOver() {
        return isGameOver;
    }

    /**
     * неужели?.
     */
    public boolean isWin() {
        return isWin;
    }

    /**
     * а где?.
     */
    public Snake getSnake() {
        return snake;
    }

    /**
     * (п)обедать.
     */
    public Food getFood() {
        return food;
    }

    /**
     * правила.
     */
    public GameConfig getConfig() {
        return config;
    }
}