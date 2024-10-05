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
        graph.addEdge(vertex2, vertex1); // Цикл

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

class GraphTest {
    private Graph<String> incidenceMatrixGraph;
    private Graph<String> adjacencyListGraph;

    @BeforeEach
    void setUp() {
        incidenceMatrixGraph = new IncidenceMatrixGraph<>();
        adjacencyListGraph = new AdjacencyListGraph<>();
    }

    @Test
    void testAddVertex() {
        Vertex<String> vertex = new Vertex<>("A");
        incidenceMatrixGraph.addVertex(vertex);
        adjacencyListGraph.addVertex(vertex);

        assertEquals(1, incidenceMatrixGraph.getAllVertices().size());
        assertEquals(1, adjacencyListGraph.getAllVertices().size());
    }

    @Test
    void testGetVertices() {
        Vertex<String> vertex1 = new Vertex<>("A");
        Vertex<String> vertex2 = new Vertex<>("B");

        incidenceMatrixGraph.addVertex(vertex1);
        incidenceMatrixGraph.addVertex(vertex2);
        adjacencyListGraph.addVertex(vertex1);
        adjacencyListGraph.addVertex(vertex2);

        List<Vertex<String>> verticesIncidence = incidenceMatrixGraph.getAllVertices();
        List<Vertex<String>> verticesAdjacency = adjacencyListGraph.getAllVertices();

        assertEquals(2, verticesIncidence.size());
        assertEquals(2, verticesAdjacency.size());
        assertTrue(verticesIncidence.contains(vertex1));
        assertTrue(verticesIncidence.contains(vertex2));
        assertTrue(verticesAdjacency.contains(vertex1));
        assertTrue(verticesAdjacency.contains(vertex2));
    }

    @Test
    void testRemoveVertex() {
        Vertex<String> vertex = new Vertex<>("A");

        incidenceMatrixGraph.addVertex(vertex);
        adjacencyListGraph.addVertex(vertex);

        incidenceMatrixGraph.removeVertex(vertex);
        adjacencyListGraph.removeVertex(vertex);

        assertEquals(0, incidenceMatrixGraph.getAllVertices().size());
        assertEquals(0, adjacencyListGraph.getAllVertices().size());
    }
}