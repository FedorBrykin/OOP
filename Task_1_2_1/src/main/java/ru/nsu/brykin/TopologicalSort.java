package ru.nsu.brykin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.HashSet;

/**
 * сорт.
 */
public class TopologicalSort<T> {
    private Graph<T> graph;

    /**
     * топсорт.
     */
    public TopologicalSort(Graph<T> graph) {
        this.graph = graph;
    }

    /**
     * сорт.
     */
    public List<String> sort() {
        List<String> sortedList = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Stack<String> stack = new Stack<>();
        for (String vertex : getAllVertices()) {
            if (!visited.contains(vertex)) {
                topologicalSortUtil(vertex, visited, stack);
            }
        }
        while (!stack.isEmpty()) {
            sortedList.add(stack.pop());
        }
        return sortedList;
    }

    /**
     * сорт+.
     */
    private void topologicalSortUtil(String vertex, Set<String> visited, Stack<String> stack) {
        visited.add(vertex);
        for (String neighbor : graph.getNeighbors(vertex)) {
            if (!visited.contains(neighbor)) {
                topologicalSortUtil(neighbor, visited, stack);
            }
        }
        stack.push(vertex);
    }

    /**
     * все вершины.
     */
    private List<String> getAllVertices() {
        List<String> vertices = new ArrayList<>();
        vertices.add(graph.HeadV());
        for (String vertex : graph.getNeighbors(graph.HeadV())) {
            vertices.add(vertex);
        }
        return vertices;
    }
}

