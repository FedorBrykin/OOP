package ru.nsu.brykin;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdjacencyMatrixGraphTest {
    private AdjacencyMatrixGraph<String> graph;

    @BeforeEach
    void setUp() {
        graph = new AdjacencyMatrixGraph<>(3);
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
    }

    @Test
    void testAddEdge() {
        Vertex<String> vertex1 = new Vertex<>("A");
        Vertex<String> vertex2 = new Vertex<>("B");

        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        graph.addEdge(vertex1, vertex2);

        List<Vertex<String>> neighbors = graph.getNeighbors(vertex1);
        assertEquals(1, neighbors.size());
        assertTrue(neighbors.contains(vertex2));
    }

    @Test
    void testRemoveEdge() {
        Vertex<String> vertex1 = new Vertex<>("A");
        Vertex<String> vertex2 = new Vertex<>("B");

        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        graph.addEdge(vertex1, vertex2);
        graph.removeEdge(vertex1, vertex2);

        List<Vertex<String>> neighbors = graph.getNeighbors(vertex1);
        assertEquals(0, neighbors.size());
    }

    @Test
    void testGetNeighbors() {
        Vertex<String> vertex1 = new Vertex<>("A");
        Vertex<String> vertex2 = new Vertex<>("B");
        Vertex<String> vertex3 = new Vertex<>("C");

        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        graph.addVertex(vertex3);

        graph.addEdge(vertex1, vertex2);
        graph.addEdge(vertex1, vertex3);

        List<Vertex<String>> neighbors = graph.getNeighbors(vertex1);
        assertEquals(2, neighbors.size());
        assertTrue(neighbors.contains(vertex2));
        assertTrue(neighbors.contains(vertex3));
    }

    @Test
    void testToString() {
        Vertex<String> vertex1 = new Vertex<>("A");
        Vertex<String> vertex2 = new Vertex<>("B");

        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        graph.addEdge(vertex1, vertex2);
        String expectedString = "[[false, true, false], [false, false, false], " +
                "[false, false, false]]";
        assertEquals(expectedString, graph.toString());
    }

    @Test
    void testHeadV() {
        assertNull(graph.HeadV()); // Граф пустой

        Vertex<String> vertex1 = new Vertex<>("A");
        graph.addVertex(vertex1);

        assertEquals(vertex1, graph.HeadV()); // Проверяем, что возвращается первая вершина
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

class AdjacencyMatrixGraphTest2 {

    private AdjacencyMatrixGraph<String> graph;
    private final String testFilePath = "testGraph.txt";

    @BeforeEach
    void setUp() {
        graph = new AdjacencyMatrixGraph<>(5); // Предположим, что максимальный размер графа 5
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
        // Создаем тестовый файл
        try (FileWriter writer = new FileWriter(testFilePath)) {
            writer.write("3\n"); // Количество вершин
            writer.write("0 1\n");
            writer.write("1 2\n");
            writer.write("2 0\n");
        }
        assertDoesNotThrow(() -> graph.readFromFile(testFilePath));
        assertEquals(3, graph.getAllVertices().size());
    }

    @Test
    void testReadFromFile_InvalidVertexCount() throws IOException {
        // Создаем файл с некорректным количеством вершин
        try (FileWriter writer = new FileWriter(testFilePath)) {
            writer.write("invalid_number\n");
        }

        // Проверяем, что при чтении выбрасывается исключение
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
        assertEquals(2, graph.getAllVertices().size());
    }

    @Test
    void testReadFromFile_NonExistentFile() {
        // Проверяем, что при чтении несуществующего файла выбрасывается исключение
        assertThrows(FileNotFoundException.class, () -> graph.readFromFile("non_existent_file.txt"));
    }
}