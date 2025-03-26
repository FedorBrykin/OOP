package ru.nsu.brykin.model;

import java.util.List;

/**
 * Избегание препятствий змейкой-роботом.
 */
public class AvoidObstaclesBehavior implements BehaviorStrategy {
    @Override
    public Direction chooseDirection(Snake snake, GameModel model) {
        Point head = snake.getHead();

        for (Direction direction : Direction.values()) {
            Point newHead = direction.movePoint(head);
            if (!model.isPointOccupied(newHead)) {
                return direction;
            }
        }

        return Direction.RIGHT;
    }
}