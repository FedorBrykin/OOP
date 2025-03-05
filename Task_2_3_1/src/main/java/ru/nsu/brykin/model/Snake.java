package ru.nsu.brykin.model;

import java.util.List;

public interface Snake {
    void move();
    void grow();
    List<Point> getBody();
    Point getHead();
    void setDirection(Direction direction);
}