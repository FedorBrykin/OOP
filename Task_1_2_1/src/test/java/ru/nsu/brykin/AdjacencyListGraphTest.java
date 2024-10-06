package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;


class AdjacencyListGraphTest {
    private AdjacencyListGraph<String> graph;

    @BeforeEach
    void setUp() {
        graph = new AdjacencyListGraph<>();
    }

    @Test
    void testAddVertex() {
        Vertex<String> vertexA = new Vertex<>("A");
        graph.addVertex(vertexA);

        assertEquals(1, graph.getAllVertices().size());
        assertTrue(graph.getAllVertices().contains(vertexA));
    }

    @Test
    void testRemoveVertex() {
        Vertex<String> vertexA = new Vertex<>("A");
        graph.addVertex(vertexA);
        graph.removeVertex(vertexA);

        assertEquals(0, graph.getAllVertices().size());
        assertFalse(graph.getAllVertices().contains(vertexA));
    }

    @Test
    void testAddEdge() {
        Vertex<String> vertexA = new Vertex<>("A");
        Vertex<String> vertexB = new Vertex<>("B");
        graph.addEdge(vertexA, vertexB);

        List<Vertex<String>> neighbors = graph.getNeighbors(vertexA);
        assertEquals(1, neighbors.size());
        assertTrue(neighbors.contains(vertexB));
    }

    @Test
    void testRemoveEdge() {
        Vertex<String> vertexA = new Vertex<>("A");
        Vertex<String> vertexB = new Vertex<>("B");
        graph.addEdge(vertexA, vertexB);
        graph.removeEdge(vertexA, vertexB);

        List<Vertex<String>> neighbors = graph.getNeighbors(vertexA);
        assertEquals(0, neighbors.size());
    }

    @Test
    void testGetNeighbors() {
        Vertex<String> vertexA = new Vertex<>("A");
        Vertex<String> vertexB = new Vertex<>("B");
        Vertex<String> vertexC = new Vertex<>("C");

        graph.addEdge(vertexA, vertexB);
        graph.addEdge(vertexA, vertexC);

        List<Vertex<String>> neighbors = graph.getNeighbors(vertexA);
        assertEquals(2, neighbors.size());
        assertTrue(neighbors.contains(vertexB));
        assertTrue(neighbors.contains(vertexC));
    }

    @Test
    void testToString() {
        Vertex<String> vertexA = new Vertex<>("A");
        Vertex<String> vertexB = new Vertex<>("B");

        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        graph.addEdge(vertexA, vertexB);

        String expectedString = "{A=[B], B=[]}";
        assertEquals(expectedString, graph.toString());
    }

    @Test
    void testGetAllVertices() {
        Vertex<String> vertexA = new Vertex<>("A");
        Vertex<String> vertexB = new Vertex<>("B");

        graph.addVertex(vertexA);
        graph.addVertex(vertexB);

        List<Vertex<String>> vertices = graph.getAllVertices();
        assertEquals(2, vertices.size());
        assertTrue(vertices.contains(vertexA));
        assertTrue(vertices.contains(vertexB));
    }

    @Test
    void testHeadV() {
        Vertex<String> vertexA = new Vertex<>("A");

        graph.addVertex(vertexA);

        assertEquals(vertexA, graph.HeadV());
    }
}
