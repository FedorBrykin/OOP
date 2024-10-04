package ru.nsu.brykin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class IncidenceMatrixGraphTest {

    private IncidenceMatrixGraph graph;

    @BeforeEach
    public void setUp() {
        graph = new IncidenceMatrixGraph();
    }

    @Test
    public void testAddVertex() {
        graph.addVertex("A");
        assertEquals(1, graph.getAllVertices().size());
        assertTrue(graph.getAllVertices().contains(new Vertex("A")));

        graph.addVertex("A");
        assertEquals(1, graph.getAllVertices().size());
    }

    @Test
    public void testRemoveVertex() {
        graph.addVertex("A");
        graph.addVertex("B");
        assertEquals(2, graph.getAllVertices().size());

        graph.removeVertex("A");
        assertEquals(1, graph.getAllVertices().size());
        assertFalse(graph.getAllVertices().contains(new Vertex("A")));
    }

    @Test
    public void testAddEdge() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");
        assertTrue(graph.getNeighbors("A").contains("B"));
    }

    @Test
    public void testRemoveEdge() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");

        assertTrue(graph.getNeighbors("A").contains("B"));

        graph.removeEdge("A", "B");

        assertFalse(graph.getNeighbors("A").contains("B"));
    }

    @Test
    public void testRemoveNonExistentVertex() {
        graph.addVertex("A");
        graph.removeVertex("B");
        assertEquals(1, graph.getAllVertices().size());
    }
}
