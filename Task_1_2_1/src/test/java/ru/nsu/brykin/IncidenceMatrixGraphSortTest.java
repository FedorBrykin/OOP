package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IncidenceMatrixGraphSortTest {
    private IncidenceMatrixGraph<String> graph;
    private final String testFilePath = "testGraph.txt";

    @BeforeEach
    void setUp() {
        graph = new IncidenceMatrixGraph<>();
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
        assertTrue(neighborsOfV0.contains(v1));
        assertTrue(neighborsOfV0.contains(v2));
        assertTrue(neighborsOfV1.contains(v0));
        assertTrue(neighborsOfV1.contains(v2));
        List<Vertex<String>> neighborsOfV2 = graph.getNeighbors(v2);
        assertTrue(neighborsOfV2.contains(v0));
        assertTrue(neighborsOfV2.contains(v1));
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

    @Test
    void testReadFromFile_NonExistentFile() {
        assertThrows(FileNotFoundException.class,
                () -> graph.readFromFile("non_existent_file.txt"));
    }
}