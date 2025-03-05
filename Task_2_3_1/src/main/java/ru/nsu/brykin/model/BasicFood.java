package ru.nsu.brykin.model;

import java.util.List;
import java.util.Random;

public class BasicFood implements Food {
    private int x, y;

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void spawn(int maxX, int maxY, List<Point> snakeBody) {
        Random random = new Random();
        do {
            x = random.nextInt(maxX);
            y = random.nextInt(maxY);
        } while (snakeBody.contains(new Point(x, y)));
    }

    @Override
    public int getScore() {
        return 1;
    }
}