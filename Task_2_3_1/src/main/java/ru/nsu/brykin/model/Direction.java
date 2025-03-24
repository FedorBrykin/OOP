package ru.nsu.brykin.model;

/**
 * компас.
 */
public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    /**
     * Возвращает новую точку после движения в текущем направлении.
     */
    public Point movePoint(Point point) {
        return switch (this) {
            case UP -> new Point(point.posX, point.posY - 1);
            case DOWN -> new Point(point.posX, point.posY + 1);
            case LEFT -> new Point(point.posX - 1, point.posY);
            case RIGHT -> new Point(point.posX + 1, point.posY);
        };
    }
}