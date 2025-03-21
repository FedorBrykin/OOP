package ru.nsu.brykin.model;

import java.util.List;

/**
 * змейка.
 */
public interface Snake {
    /**
     * ходим.
     */
    void move();

    /**
     * растём.
     */
    void grow();

    /**
     * тело.
     */
    List<Point> getBody();

    /**
     * голова.
     */
    Point getHead();

    /**
     * направление.
     */
    void setDirection(Direction direction);
}
