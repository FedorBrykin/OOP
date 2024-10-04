package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class AdjacencyListGraphTest {
    private AdjacencyListGraph graph;

    @BeforeEach
    void setUp() {
        graph = new AdjacencyListGraph();
    }

    @Test
    void testAddVertex() {
        graph.addVertex("A");
        assertTrue(graph.getNeighbors("A").isEmpty());
        graph.addVertex("A");
        assertTrue(graph.getNeighbors("A").isEmpty());
    }

    @Test
    void testRemoveVertex() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");

        graph.removeVertex("A");
        assertFalse(graph.getNeighbors("B").contains("A"));
        assertTrue(graph.getNeighbors("B").isEmpty());
        assertTrue(graph.getNeighbors("A").isEmpty());
    }

    @Test
    void testAddEdge() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");

        List<String> neighborsOfA = graph.getNeighbors("A");
        assertEquals(1, neighborsOfA.size());
        assertTrue(neighborsOfA.contains("B"));
    }

    @Test
    void testRemoveEdge() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");
        graph.removeEdge("A", "B");

        assertFalse(graph.getNeighbors("A").contains("B"));
    }

    @Test
    void testGetNeighbors() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");

        List<String> neighbors = graph.getNeighbors("A");
        assertEquals(1, neighbors.size());
        assertTrue(neighbors.contains("B"));

        neighbors = graph.getNeighbors("B");
        assertTrue(neighbors.isEmpty());
    }

    @Test
    void testToString() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");

        String graphString = graph.toString();
        assertTrue(graphString.contains("A"));
        assertTrue(graphString.contains("B"));
        assertTrue(graphString.contains("A=[B]"));
    }
}