package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * test.
 */
public class EdgeTest {

    @Test
    public void testConstructor() {
        Vertex from = new Vertex("A");
        Vertex to = new Vertex("B");
        Edge edge = new Edge(from, to);

        assertNotNull(edge);
        assertEquals(from, edge.getFrom());
        assertEquals(to, edge.getTo());
    }

    @Test
    public void testGetFrom() {
        Vertex from = new Vertex("C");
        Vertex to = new Vertex("D");
        Edge edge = new Edge(from, to);

        assertEquals(from, edge.getFrom());
    }

    @Test
    public void testGetTo() {
        Vertex from = new Vertex("E");
        Vertex to = new Vertex("F");
        Edge edge = new Edge(from, to);

        assertEquals(to, edge.getTo());
    }
}
