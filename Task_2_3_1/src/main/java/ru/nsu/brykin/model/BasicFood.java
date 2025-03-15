package ru.nsu.brykin.model;

import java.util.List;
import java.util.Random;

/**
 * еда.
 */
public class BasicFood implements Food {
    private int x, y;

    /**
     * координата по x.
     */
    @Override
    public int getX() {
        return x;
    }

    /**
     * координата по y.
     */
    @Override
    public int getY() {
        return y;
    }

    /**
     * спавн.
     */
    @Override
    public void spawn(int maxX, int maxY, List<Point> snakeBody) {
        Random random = new Random();
        do {
            x = random.nextInt(maxX);
            y = random.nextInt(maxY);
        } while (snakeBody.contains(new Point(x, y)));
    }

    /**
     * +1.
     */
    @Override
    public int getScore() {
        return 1;
    }
}