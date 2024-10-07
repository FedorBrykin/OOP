package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IncidenceMatrixGraphTest {
    private IncidenceMatrixGraph<String> graph;

    @BeforeEach
    void setUp() {
        graph = new IncidenceMatrixGraph<>();
    }

    @Test
    void testAddVertex() {
        Vertex<String> vertex1 = new Vertex<>("A");
        Vertex<String> vertex2 = new Vertex<>("B");

        graph.addVertex(vertex1);
        graph.addVertex(vertex2);

        assertEquals(2, graph.getAllVertices().size());
        assertTrue(graph.getAllVertices().contains(vertex1));
        assertTrue(graph.getAllVertices().contains(vertex2));
    }

    @Test
    void testRemoveVertex() {
        Vertex<String> vertex1 = new Vertex<>("A");
        Vertex<String> vertex2 = new Vertex<>("B");
        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        graph.removeVertex(vertex1);
        assertEquals(1, graph.getAllVertices().size());
        assertFalse(graph.getAllVertices().contains(vertex1));
        assertTrue(graph.getAllVertices().contains(vertex2));
    }

    @Test
    void testAddEdge() {
        Vertex<String> vertex1 = new Vertex<>("A");
        Vertex<String> vertex2 = new Vertex<>("B");

        graph.addEdge(vertex1, vertex2);

        List<Vertex<String>> neighbors = graph.getNeighbors(vertex1);
        assertEquals(1, neighbors.size());
        assertTrue(neighbors.contains(vertex2));

        neighbors = graph.getNeighbors(vertex2);
        assertEquals(1, neighbors.size());
        assertTrue(neighbors.contains(vertex1));
    }

    @Test
    void testRemoveEdge() {
        Vertex<String> vertex1 = new Vertex<>("A");
        Vertex<String> vertex2 = new Vertex<>("B");

        graph.addEdge(vertex1, vertex2);
        graph.removeEdge(vertex1, vertex2);

        List<Vertex<String>> neighbors = graph.getNeighbors(vertex1);
        assertEquals(0, neighbors.size());

        neighbors = graph.getNeighbors(vertex2);
        assertEquals(0, neighbors.size());
    }

    @Test
    void testGetNeighbors() {
        Vertex<String> vertex1 = new Vertex<>("A");
        Vertex<String> vertex2 = new Vertex<>("B");
        Vertex<String> vertex3 = new Vertex<>("C");
        graph.addEdge(vertex1, vertex2);
        graph.addEdge(vertex1, vertex3);
        List<Vertex<String>> neighbors = graph.getNeighbors(vertex1);
        assertEquals(2, neighbors.size());
        assertTrue(neighbors.contains(vertex2));
        assertTrue(neighbors.contains(vertex3));
    }

    @Test
    void testheadV() {
        assertNull(graph.headV());
        Vertex<String> vertex1 = new Vertex<>("A");
        graph.addVertex(vertex1);
        assertEquals(vertex1, graph.headV());
    }

    @Test
    void testGetAllVertices() {
        Vertex<String> vertex1 = new Vertex<>("A");
        Vertex<String> vertex2 = new Vertex<>("B");

        graph.addVertex(vertex1);
        graph.addVertex(vertex2);

        List<Vertex<String>> vertices = graph.getAllVertices();

        assertEquals(2, vertices.size());
        assertTrue(vertices.contains(vertex1));
        assertTrue(vertices.contains(vertex2));
    }
}