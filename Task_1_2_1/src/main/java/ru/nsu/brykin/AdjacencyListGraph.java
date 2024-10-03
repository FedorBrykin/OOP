package ru.nsu.brykin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * список смежности.
 */
class AdjacencyListGraph implements Graph {
    private Map<String, List<String>> adjList;

    /**
     * new.
     */
    public AdjacencyListGraph() {
        adjList = new HashMap<>();
    }

    /**
     * добавление вершины.
     */
    @Override
    public void addVertex(String vertex) {
        if (!adjList.containsKey(vertex)) {
            adjList.put(vertex, new ArrayList<>());
        }
    }

    /**
     * удаление вершины.
     */
    @Override
    public void removeVertex(String vertex) {
        adjList.remove(vertex);
        adjList.values().forEach(e -> e.remove(vertex));
    }

    /**
     * добавление ребра.
     */
    @Override
    public void addEdge(String fromVertex, String toVertex) {
        addVertex(fromVertex);
        addVertex(toVertex);
        adjList.get(fromVertex).add(toVertex);
    }

    /**
     * удаление ребра.
     */
    @Override
    public void removeEdge(String fromVertex, String toVertex) {
        if (adjList.containsKey(fromVertex)) {
            adjList.get(fromVertex).remove(toVertex);
        }
    }

    /**
     * соседи.
     */
    @Override
    public List<String> getNeighbors(String vertex) {
        return adjList.getOrDefault(vertex, new ArrayList<>());
    }

    /**
     * чтение из файла.
     */
    @Override
    public void readFromFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                addVertex(parts[0]);
                addVertex(parts[1]);
                addEdge(parts[0], parts[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * toString.
     */
    @Override
    public String toString() {
        return adjList.toString();
    }

    /**
     * сортировка.
     */
    public List<String> topologicalSort() {
        Map<String, Boolean> visited = new HashMap<>();
        Stack<String> stack = new Stack<>();
        for (String vertex : adjList.keySet()) {
            if (!visited.getOrDefault(vertex, false)) {
                topologicalSortUtil(vertex, visited, stack);
            }
        }
        List<String> sortedList = new ArrayList<>();
        while (!stack.empty()) {
            sortedList.add(stack.pop());
        }
        return sortedList;
    }

    /**
     * для сортировки.
     */
    private void topologicalSortUtil(String vertex, Map<String, Boolean> visited,
                                     Stack<String> stack) {
        visited.put(vertex, true);
        for (String neighbor : adjList.get(vertex)) {
            if (!visited.getOrDefault(neighbor, false)) {
                topologicalSortUtil(neighbor, visited, stack);
            }
        }
        stack.push(vertex);
    }
}
