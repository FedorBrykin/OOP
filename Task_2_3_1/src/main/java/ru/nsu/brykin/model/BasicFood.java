package ru.nsu.brykin.model;

import java.util.List;
import java.util.Random;

/**
 * еда.
 */
public class BasicFood implements Food {
    private int posX;
    private int posY;

    /**
     * координата по posX.
     */
    @Override
    public int getX() {
        return posX;
    }

    /**
     * координата по posY.
     */
    @Override
    public int getY() {
        return posY;
    }

    /**
     * спавн.
     */
    @Override
    public void spawn(int maxX, int maxY, List<Point> snakeBody) {
        Random random = new Random();
        do {
            posX = random.nextInt(maxX);
            posY = random.nextInt(maxY);
        } while (snakeBody.contains(new Point(posX, posY)));
    }

    /**
     * +1.
     */
    @Override
    public int getScore() {
        return 1;
    }
}
