package ru.nsu.brykin.model;

import java.util.LinkedList;
import java.util.List;

public class BasicSnake implements Snake {
    private LinkedList<Point> body;
    private Direction direction;

    public BasicSnake(int startX, int startY, int initialLength) {
        body = new LinkedList<>();
        for (int i = 0; i < initialLength; i++) {
            body.add(new Point(startX - i, startY));
        }
        direction = Direction.RIGHT;
    }

    @Override
    public void move() {
        Point head = getHead();
        Point newHead = new Point(head.x, head.y);

        switch (direction) {
            case UP:
                newHead.y--;
                break;
            case DOWN:
                newHead.y++;
                break;
            case LEFT:
                newHead.x--;
                break;
            case RIGHT:
                newHead.x++;
                break;
        }

        body.addFirst(newHead);
        body.removeLast();
    }

    @Override
    public void grow() {
        Point tail = body.getLast();
        body.addLast(new Point(tail.x, tail.y));
    }

    @Override
    public List<Point> getBody() {
        return body;
    }

    @Override
    public Point getHead() {
        return body.getFirst();
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}