package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.List;

class TopologicalSortTest {
    @Test
    void testTopologicalSort1() {
        AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph(6);
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        TopologicalSort newSort = new TopologicalSort(graph);
        List<String> sorted = newSort.sort();
        assertEquals(3, sorted.size());
        assertTrue(sorted.indexOf("A") < sorted.indexOf("B"));
        assertTrue(sorted.indexOf("B") < sorted.indexOf("C"));
    }

    @Test
    void testTopologicalSort2() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        TopologicalSort newSort = new TopologicalSort(graph);
        List<String> sorted = newSort.sort();
        assertEquals(3, sorted.size());
        assertTrue(sorted.indexOf("A") < sorted.indexOf("B"));
        assertTrue(sorted.indexOf("B") < sorted.indexOf("C"));
    }

    @Test
    void testTopologicalSort3() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "D");
        graph.addEdge("C", "D");
        TopologicalSort newSort1 = new TopologicalSort(graph);
        List<String> sorted1 = newSort1.sort();
        assertTrue(sorted1.indexOf("A") < sorted1.indexOf("B"));
        assertTrue(sorted1.indexOf("A") < sorted1.indexOf("C"));
        assertTrue(sorted1.indexOf("B") < sorted1.indexOf("D"));
        assertTrue(sorted1.indexOf("C") < sorted1.indexOf("D"));

        TopologicalSort newSort2 = new TopologicalSort(graph);
        List<String> sorted2 = newSort2.sort();
        assertTrue(sorted2.indexOf("A") < sorted2.indexOf("B"));
        assertTrue(sorted2.indexOf("A") < sorted2.indexOf("C"));
        assertTrue(sorted2.indexOf("B") < sorted2.indexOf("D"));
        assertTrue(sorted2.indexOf("C") < sorted2.indexOf("D"));
    }
}