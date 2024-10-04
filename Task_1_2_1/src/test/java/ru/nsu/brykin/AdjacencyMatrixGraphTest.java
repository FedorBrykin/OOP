package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class AdjacencyMatrixGraphTest {
    private AdjacencyMatrixGraph graph;

    @BeforeEach
    void setUp() {
        graph = new AdjacencyMatrixGraph(5);
    }

    @Test
    void testAddVertex() {
        graph.addVertex("A");
        graph.addVertex("B");
        assertTrue(graph.toString().contains("A"));
        assertTrue(graph.toString().contains("B"));
    }

    @Test
    void testRemoveVertex() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.removeVertex("A");
        assertFalse(graph.vertexIndexMap.containsKey("A"));
    }

    @Test
    void testAddEdge() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");
        assertTrue(graph.getNeighbors("A").contains("B"));
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
        graph.addVertex("C");
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        List<String> neighbors = graph.getNeighbors("A");
        assertTrue(neighbors.contains("B"));
        assertTrue(neighbors.contains("C"));
        assertFalse(neighbors.contains("A"));
    }
}