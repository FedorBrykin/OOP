package ru.nsu.brykin.model;

import java.util.List;

public interface Food {
    int getX();
    int getY();
    void spawn(int maxX, int maxY, List<Point> snakeBody);
    int getScore();
}