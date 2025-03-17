package ru.nsu.brykin.model;

import java.util.LinkedList;
import java.util.List;

/**
 * змея.
 */
public class BasicSnake implements Snake {
    private LinkedList<Point> body;
    private Direction direction;

    /**
     * создаём змею.
     */
    public BasicSnake(int startX, int startY, int initialLength) {
        body = new LinkedList<>();
        for (int i = 0; i < initialLength; i++) {
            body.add(new Point(startX - i, startY));
        }
        direction = Direction.RIGHT;
    }

    /**
     * перемещения.
     */
    @Override
    public void move() {
        Point head = getHead();
        Point newHead = new Point(head.posX, head.posY);

        switch (direction) {
            case UP:
                newHead.posY--;
                break;
            case DOWN:
                newHead.posY++;
                break;
            case LEFT:
                newHead.posX--;
                break;
            case RIGHT:
                newHead.posX++;
                break;
            default:
                throw new IllegalStateException("Unexpected direction: " + direction);
        }

        body.addFirst(newHead);
        body.removeLast();
    }

    /**
     * рост после еды.
     */
    @Override
    public void grow() {
        Point tail = body.getLast();
        body.addLast(new Point(tail.posX, tail.posY));
    }

    /**
     * информация о теле.
     */
    @Override
    public List<Point> getBody() {
        return body;
    }

    /**
     * а голову ты дома не забыл?.
     */
    @Override
    public Point getHead() {
        return body.getFirst();
    }

    /**
     * ну и куда идти.
     */
    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}