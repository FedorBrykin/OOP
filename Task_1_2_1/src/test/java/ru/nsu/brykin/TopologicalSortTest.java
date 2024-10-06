package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class TopologicalSortTest {
    private TopologicalSort<String> topologicalSort;
    private Graph<String> graph;

    @BeforeEach
    void setUp() {
        topologicalSort = new TopologicalSort<>();
        graph = new IncidenceMatrixGraph<>();
    }

    @Test
    void testSortWithNoEdges() {
        Vertex<String> vertex1 = new Vertex<>("A");
        Vertex<String> vertex2 = new Vertex<>("B");

        graph.addVertex(vertex1);
        graph.addVertex(vertex2);

        List<Vertex<String>> sortedList = topologicalSort.sort(graph);
        assertEquals(2, sortedList.size());
        assertTrue(sortedList.contains(vertex1));
        assertTrue(sortedList.contains(vertex2));
    }

    @Test
    void testSortWithCycle() {
        Vertex<String> vertex1 = new Vertex<>("A");
        Vertex<String> vertex2 = new Vertex<>("B");
        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        graph.addEdge(vertex1, vertex2);
        graph.addEdge(vertex2, vertex1);
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            topologicalSort.sort(graph);
        });
        assertEquals("Graph has at least one cycle.", exception.getMessage());
    }

    @Test
    void testSortEmptyGraph() {
        List<Vertex<String>> sortedList = topologicalSort.sort(graph);
        assertTrue(sortedList.isEmpty());
    }
}