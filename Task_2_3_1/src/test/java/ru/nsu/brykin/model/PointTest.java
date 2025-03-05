package ru.nsu.brykin.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    @Test
    void testEquals() {
        Point p1 = new Point(5, 5);
        Point p2 = new Point(5, 5);
        Point p3 = new Point(6, 6);
        assertTrue(p1.equals(p2));
        assertFalse(p1.equals(p3));
    }
}