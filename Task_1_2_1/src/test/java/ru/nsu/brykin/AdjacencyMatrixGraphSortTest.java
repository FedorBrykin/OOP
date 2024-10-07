package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

class AdjacencyMatrixGraphSortTest {

    private AdjacencyMatrixGraph<String> graph;
    private final String testFilePath = "testGraph.txt";

    @BeforeEach
    void setUp() {
        graph = new AdjacencyMatrixGraph<>(5);
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
        assertEquals(3, graph.getAllVertices().size());
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
        assertEquals(2, graph.getAllVertices().size());
    }

    @Test
    void testReadFromFile_NonExistentFile() {
        assertThrows(FileNotFoundException.class, () -> graph.readFromFile("non_existent_file.txt"));
    }
}