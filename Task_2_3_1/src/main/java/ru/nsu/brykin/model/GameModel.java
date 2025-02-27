package ru.nsu.brykin.model;

public class GameModel {

    private SnakeModel snake;
    private FoodModel food;
    private int width, height;
    private int foodCount;
    private boolean isGameOver;

    public GameModel(int width, int height, int foodCount) {
        this.width = width;
        this.height = height;
        this.foodCount = foodCount;
        this.isGameOver = false;
        snake = new SnakeModel(width / 2, height / 2);
        food = new FoodModel(width, height);
    }

    public void update() {
        if (isGameOver) {
            return;
        }

        snake.move();
        checkCollision();
        checkFood();
    }

    private void checkCollision() {
        SnakeModel.Point head = snake.getHead();
        if (head.x < 0 || head.x >= width || head.y < 0 || head.y >= height) {
            gameOver();
            return;
        }

        for (SnakeModel.Point bodyPart : snake.getBody()) {
            if (bodyPart != head && bodyPart.x == head.x && bodyPart.y == head.y) {
                gameOver();
                return;
            }
        }
    }

    private void checkFood() {
        if (snake.getHead().x == food.x && snake.getHead().y == food.y) {
            snake.grow();
            food.spawn(width, height);
        }
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    private void gameOver() {
        isGameOver = true;
    }

    public SnakeModel getSnake() {
        return snake;
    }

    public FoodModel getFood() {
        return food;
    }
}