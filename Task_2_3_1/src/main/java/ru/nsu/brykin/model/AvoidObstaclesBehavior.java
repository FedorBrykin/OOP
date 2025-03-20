package ru.nsu.brykin.model;

import java.util.List;

/**
 * Избегание препятствий змейкой-роботом.
 */
public class AvoidObstaclesBehavior implements BehaviorStrategy {
    @Override
    public Direction chooseDirection(Snake snake, GameModel model) {
        Point head = snake.getHead();
        List<Point> obstacles = model.getObstacles();

        for (Direction direction : Direction.values()) {
            Point newHead = moveInDirection(head, direction);
            if (!isCollision(newHead, model)) {
                return direction;
            }
        }

        return Direction.RIGHT;
    }

    private Point moveInDirection(Point head, Direction direction) {
        return switch (direction) {
            case UP -> new Point(head.posX, head.posY - 1);
            case DOWN -> new Point(head.posX, head.posY + 1);
            case LEFT -> new Point(head.posX - 1, head.posY);
            case RIGHT -> new Point(head.posX + 1, head.posY);
        };
    }

    private boolean isCollision(Point point, GameModel model) {
        if (point.posX < 0 || point.posX >= model.getConfig().getWidth()
                || point.posY < 0 || point.posY >= model.getConfig().getHeight()) {
            return true;
        }

        for (Snake otherSnake : model.getSnakes()) {
            if (otherSnake.getBody().contains(point)) {
                return true;
            }
        }
        return false;
    }
}