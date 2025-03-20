package ru.nsu.brykin.model;

import java.util.ArrayList;
import java.util.List;

/**
 * В игру Джон, в игру!.
 */
public class GameModel {
    private final List<Snake> snakes = new ArrayList<>();
    private final Food food;
    private final GameConfig config;
    private boolean isGameOver;
    private boolean isWin;

    /**
     * В игру Джон, в игру!).
     */
    public GameModel(GameConfig config) {
        this.config = config;

        snakes.add(new BasicSnake(config.getWidth() / 2, config.getHeight() / 2,
                config.getInitialSnakeLength()));

        snakes.add(new RobotSnake(5, 5, config.getInitialSnakeLength(), new RandomBehavior()));
        snakes.add(new RobotSnake(15, 15, config.getInitialSnakeLength(), new ChaseFoodBehavior()));
        snakes.add(new RobotSnake(10, 10, config.getInitialSnakeLength(), new AvoidObstaclesBehavior()));

        food = new BasicFood();
        food.spawn(config.getWidth(), config.getHeight(), getAllSnakeBodies());
    }

    /**
     * релиз не ждёт.
     */
    public void update() {
        if (isGameOver) {
            return;
        }

        for (Snake snake : snakes) {
            if (snake instanceof RobotSnake) {
                ((RobotSnake) snake).updateDirection(this);
            }
            snake.move();
        }

        checkCollisions();
        checkFood();
        checkWin();
    }

    private List<Point> getAllSnakeBodies() {
        List<Point> allBodies = new ArrayList<>();
        for (Snake snake : snakes) {
            allBodies.addAll(snake.getBody());
        }
        return allBodies;
    }

    /**
     * Титаник должен видеть айсберг!.
     */
    private void checkCollisions() {
        Snake playerSnake = snakes.get(0);
        Point playerHead = playerSnake.getHead();
        if (playerHead.posX < 0 || playerHead.posX >= config.getWidth()
                || playerHead.posY < 0 || playerHead.posY >= config.getHeight()) {
            gameOver();
            return;
        }
        for (Snake otherSnake : snakes) {
            if (otherSnake != playerSnake && otherSnake.getBody().contains(playerHead)) {
                gameOver();
                return;
            }
        }
        for (Point bodyPart : playerSnake.getBody()) {
            if (bodyPart != playerHead && bodyPart.equals(playerHead)) {
                gameOver();
                return;
            }
        }
        for (int i = 1; i < snakes.size(); i++) {
            Snake robotSnake = snakes.get(i);
            Point robotHead = robotSnake.getHead();
            if (robotHead.posX < 0 || robotHead.posX >= config.getWidth()
                    || robotHead.posY < 0 || robotHead.posY >= config.getHeight()) {
                // Змейка-робот умирает, но игра продолжается
                snakes.remove(i);
                i--;
                continue;
            }
            for (Snake otherSnake : snakes) {
                if (otherSnake != robotSnake && otherSnake.getBody().contains(robotHead)) {
                    snakes.remove(i);
                    i--;
                    break;
                }
            }

            for (Point bodyPart : robotSnake.getBody()) {
                if (bodyPart != robotHead && bodyPart.equals(robotHead)) {
                    snakes.remove(i);
                    i--;
                    break;
                }
            }
        }
    }

    /**
     * есть поесть?.
     */
    private void checkFood() {
        for (Snake snake : snakes) {
            if (snake.getHead().posX == food.getX() && snake.getHead().posY == food.getY()) {
                snake.grow();
                food.spawn(config.getWidth(), config.getHeight(), getAllSnakeBodies());
            }
        }
    }

    /**
     * победа.
     */
    private void checkWin() {
        for (Snake snake : snakes) {
            if (snake.getBody().size() >= config.getWinLength()) {
                isWin = true;
                gameOver();
            }
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
    public List<Snake> getSnakes() {
        return snakes;
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

    /**
     * Препятствия.
     */
    public List<Point> getObstacles() {
        List<Point> obstacles = new ArrayList<>();
        for (Snake snake : snakes) {
            obstacles.addAll(snake.getBody());
        }
        return obstacles;
    }
}