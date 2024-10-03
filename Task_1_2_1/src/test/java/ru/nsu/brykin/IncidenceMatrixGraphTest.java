package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

class IncidenceMatrixGraphTest {
    private IncidenceMatrixGraph graph;

    @BeforeEach
    void setUp() {
        graph = new IncidenceMatrixGraph();
    }

    @Test
    void testAddVertex() {
        graph.addVertex("A");
        assertTrue(graph.vertexIndex.containsKey("A"));
        assertEquals(0, graph.vertexIndex.get("A"));
    }

    @Test
    void testRemoveVertex() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");

        graph.removeVertex("A");
        assertFalse(graph.vertexIndex.containsKey("A"));
        assertTrue(graph.vertexIndex.containsKey("B"));
        assertTrue(graph.incidenceMatrix.length == 1);
    }

    @Test
    void testAddEdge() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");

        assertEquals(1, graph.edgeCount);
        assertEquals(1, graph.incidenceMatrix[graph.vertexIndex.get("A")][0]);
        assertEquals(-1, graph.incidenceMatrix[graph.vertexIndex.get("B")][0]);
    }

    @Test
    void testRemoveEdge() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");

        graph.removeEdge("A", "B");
        assertEquals(1, graph.edgeCount); // количество ребер остается, инцидентность обнуляется
        assertEquals(0, graph.incidenceMatrix[graph.vertexIndex.get("A")][0]);
        assertEquals(0, graph.incidenceMatrix[graph.vertexIndex.get("B")][0]);
    }

    @Test
    void testGetNeighbors() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");

        List<String> neighbors = graph.getNeighbors("A");
        assertEquals(1, neighbors.size());
        assertTrue(neighbors.contains("B"));

        neighbors = graph.getNeighbors("B");
        assertTrue(neighbors.isEmpty());
    }

    @Test
    void testToString() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");

        String graphString = graph.toString();
        assertTrue(graphString.contains("A,B"));
    }

    @Test
    public void testTopologicalSort_NoCycles() {
        // Настройка графа без циклов
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "D");
        graph.addEdge("C", "D");

        List<String> sorted = graph.topologicalSort();

        // Проверяем, что D находится после B и C
        assertTrue(sorted.indexOf("A") < sorted.indexOf("B"));
        assertTrue(sorted.indexOf("A") < sorted.indexOf("C"));
        assertTrue(sorted.indexOf("B") < sorted.indexOf("D"));
        assertTrue(sorted.indexOf("C") < sorted.indexOf("D"));
    }

    @Test
    public void testTopologicalSort_MultipleValidOrders() {
        // Настройка графа без циклов
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "D");
        graph.addEdge("C", "D");

        List<String> sorted1 = graph.topologicalSort();

        // Проверяем, что A всегда перед B и C, а B и C перед D
        assertTrue(sorted1.indexOf("A") < sorted1.indexOf("B"));
        assertTrue(sorted1.indexOf("A") < sorted1.indexOf("C"));
        assertTrue(sorted1.indexOf("B") < sorted1.indexOf("D"));
        assertTrue(sorted1.indexOf("C") < sorted1.indexOf("D"));

        // Повторный вызов должен вернуть другой допустимый порядок
        List<String> sorted2 = graph.topologicalSort();

        // Проверяем, что порядок также корректен
        assertTrue(sorted2.indexOf("A") < sorted2.indexOf("B"));
        assertTrue(sorted2.indexOf("A") < sorted2.indexOf("C"));
        assertTrue(sorted2.indexOf("B") < sorted2.indexOf("D"));
        assertTrue(sorted2.indexOf("C") < sorted2.indexOf("D"));
    }

    @Test
    public void testTopologicalSort_WithCycles() {
        // Настройка графа с циклом
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A"); // Цикл здесь

        Exception exception = assertThrows(RuntimeException.class, () -> {
            graph.topologicalSort();
        });

        assertEquals("Граф содержит циклы!", exception.getMessage());
    }

    @Test
    public void testTopologicalSort_EmptyGraph() {
        // Пустой граф
        List<String> sorted = graph.topologicalSort();

        assertTrue(sorted.isEmpty());
    }

    @Test
    public void testTopologicalSort_SingleVertex() {
        // Граф с одной вершиной
        graph.addVertex("A");

        List<String> sorted = graph.topologicalSort();

        assertEquals(Collections.singletonList("A"), sorted);
    }
}
