package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class VertexTest {

    @Test
    public void testConstructor() {
        Vertex vertex = new Vertex("A");
        assertNotNull(vertex);
        assertEquals("A", vertex.getName());
    }

    @Test
    public void testGetName() {
        Vertex vertex = new Vertex("B");
        assertEquals("B", vertex.getName());
    }

    @Test
    public void testToString() {
        Vertex vertex = new Vertex("C");
        assertEquals("C", vertex.toString());
    }

    @Test
    public void testEquals_SameObject() {
        Vertex vertex = new Vertex("D");
        assertTrue(vertex.equals(vertex));
    }

    @Test
    public void testEquals_SameName() {
        Vertex vertex1 = new Vertex("E");
        Vertex vertex2 = new Vertex("E");
        assertTrue(vertex1.equals(vertex2));
    }

    @Test
    public void testEquals_DifferentName() {
        Vertex vertex1 = new Vertex("F");
        Vertex vertex2 = new Vertex("G");
        assertFalse(vertex1.equals(vertex2));
    }

    @Test
    public void testEquals_NonVertexObject() {
        Vertex vertex = new Vertex("H");
        assertFalse(vertex.equals(new Object()));
    }

    @Test
    public void testHashCode() {
        Vertex vertex1 = new Vertex("I");
        Vertex vertex2 = new Vertex("I");
        assertEquals(vertex1.hashCode(), vertex2.hashCode());

        Vertex vertex3 = new Vertex("J");
        assertNotEquals(vertex1.hashCode(), vertex3.hashCode());
    }
}
