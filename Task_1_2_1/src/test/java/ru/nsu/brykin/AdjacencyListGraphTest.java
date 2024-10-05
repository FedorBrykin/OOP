package ru.nsu.brykin;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

class AdjacencyListGraphTest2 {
    private AdjacencyListGraph<String> graph;
    private final String testFilePath = "testGraph.txt";

    @BeforeEach
    void setUp() {
        graph = new AdjacencyListGraph<>();
    }

    @AfterEach
    void tearDown() {
        File file = new File(testFilePath);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testReadFromFile_ValidInput() throws IOException {
        try (FileWriter writer = new FileWriter(testFilePath)) {
            writer.write("3\n");
            writer.write("0 1\n");
            writer.write("1 2\n");
            writer.write("2 0\n");
        }
        assertDoesNotThrow(() -> graph.readFromFile(testFilePath));
        Vertex<String> v0 = new Vertex<>("0");
        Vertex<String> v1 = new Vertex<>("1");
        Vertex<String> v2 = new Vertex<>("2");
        List<Vertex<String>> neighborsOfV0 = graph.getNeighbors(v0);
        List<Vertex<String>> neighborsOfV1 = graph.getNeighbors(v1);
        List<Vertex<String>> neighborsOfV2 = graph.getNeighbors(v2);
        assertTrue(neighborsOfV0.contains(v1));
        assertTrue(neighborsOfV1.contains(v2));
        assertTrue(neighborsOfV2.contains(v0));
    }

    @Test
    void testReadFromFile_InvalidVertexCount() throws IOException {
        try (FileWriter writer = new FileWriter(testFilePath)) {
            writer.write("invalid_number\n");
        }
        assertThrows(NumberFormatException.class, () -> graph.readFromFile(testFilePath));
    }

    @Test
    void testReadFromFile_InvalidEdgeFormat() throws IOException {
        try (FileWriter writer = new FileWriter(testFilePath)) {
            writer.write("2\n");
            writer.write("0 1\n");
            writer.write("invalid_edge_format\n");
        }
        assertDoesNotThrow(() -> graph.readFromFile(testFilePath));
        assertEquals(1, graph.getNeighbors(new Vertex<>("0")).size());
    }
}
