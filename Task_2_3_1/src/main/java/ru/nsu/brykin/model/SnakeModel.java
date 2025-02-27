package ru.nsu.brykin.model;

import java.util.LinkedList;

public class SnakeModel {

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public static class Point {
        public int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private LinkedList<Point> body;
    private Direction direction;

    public SnakeModel(int startX, int startY) {
        body = new LinkedList<>();
        body.add(new Point(startX, startY));
        direction = Direction.RIGHT;
    }

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

    public void grow() {
        Point tail = getTail();
        body.addLast(new Point(tail.x, tail.y));
    }

    public Point getHead() {
        return body.getFirst();
    }

    public Point getTail() {
        return body.getLast();
    }

    public LinkedList<Point> getBody() {
        return body;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}