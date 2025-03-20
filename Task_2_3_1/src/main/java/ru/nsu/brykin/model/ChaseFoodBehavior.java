package ru.nsu.brykin.model;

/**
 * Преследование еды змейкой-роботом.
 */
public class ChaseFoodBehavior implements BehaviorStrategy {
    @Override
    public Direction chooseDirection(Snake snake, GameModel model) {
        Point head = snake.getHead();
        int foodX = model.getFood().getX();
        int foodY = model.getFood().getY();

        if (head.posX < foodX) {
            return Direction.RIGHT;
        } else if (head.posX > foodX) {
            return Direction.LEFT;
        } else if (head.posY < foodY) {
            return Direction.DOWN;
        } else if (head.posY > foodY) {
            return Direction.UP;
        }

        return Direction.RIGHT;
    }
}
