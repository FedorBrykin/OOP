package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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