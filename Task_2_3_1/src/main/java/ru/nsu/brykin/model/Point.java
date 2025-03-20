package ru.nsu.brykin.model;

/**
 * очки.
 */
public class Point {
    public int posX;
    public int posY;

    /**
     * очки.
     */
    public Point(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * ==.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point) {
            Point other = (Point) obj;
            return this.posX == other.posX && this.posY == other.posY;
        }
        return false;
    }

    /**
     * hashCode.
     */
    @Override
    public int hashCode() {
        return 31 * posX + posY;
    }
}
