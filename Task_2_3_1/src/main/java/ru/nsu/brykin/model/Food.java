package ru.nsu.brykin.model;

import java.util.List;

/**
 * ЕДААА.
 */
public interface Food {
    /**
     * координаты по x.
     */
    int getX();

    /**
     * координаты по y.
     */
    int getY();

    /**
     * собираем вместе.
     */
    void spawn(int maxX, int maxY, List<Point> snakeBody);

    /**
     * "счёт на табло".
     */
    int getScore();
}
