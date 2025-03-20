package ru.nsu.brykin.model;

import java.util.Random;

/**
 * Случайное поведение змейки-робота.
 */
public class RandomBehavior implements BehaviorStrategy {
    private final Random random = new Random();

    @Override
    public Direction chooseDirection(Snake snake, GameModel model) {
        Direction[] directions = Direction.values();
        return directions[random.nextInt(directions.length)];
    }
}