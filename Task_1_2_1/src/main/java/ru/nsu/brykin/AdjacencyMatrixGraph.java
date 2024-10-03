package ru.nsu.brykin;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class AdjacencyMatrixGraph implements Graph {
    Map<String, Integer> vertexIndexMap;
    String[] vertices;
    private boolean[][] adjMatrix;
    private int vertexCount;

    public AdjacencyMatrixGraph(int capacity) {
        vertexIndexMap = new HashMap<>();
        vertices = new String[capacity];
        adjMatrix = new boolean[capacity][capacity];
        vertexCount = 0;
    }

    /**
     * добавление вершины.
     */
    @Override
    public void addVertex(String vertex) {
        if (!vertexIndexMap.containsKey(vertex)) {
            vertexIndexMap.put(vertex, vertexCount);
            vertices[vertexCount++] = vertex;
        }
    }

    /**
     * удаление вершины.
     */
    @Override
    public void removeVertex(String vertex) {
        if (vertexIndexMap.containsKey(vertex)) {
            int index = vertexIndexMap.remove(vertex);
            for (int i = 0; i < vertexCount; i++) {
                adjMatrix[i][index] = false; // Удаление всех рёбер
                adjMatrix[index][i] = false;
            }
        }
        for (int i = 0; i < vertexCount; i++) {
            if (Objects.equals(vertices[i], vertex)) {
                vertices[i].replace(vertex, "");
            }
        }
    }

    /**
     * добавление ребра.
     */
    @Override
    public void addEdge(String fromVertex, String toVertex) {
        if (vertexIndexMap.containsKey(fromVertex) && vertexIndexMap.containsKey(toVertex)) {
            int fromIndex = vertexIndexMap.get(fromVertex);
            int toIndex = vertexIndexMap.get(toVertex);
            adjMatrix[fromIndex][toIndex] = true;
        }
    }

    /**
     * удаление ребра.
     */
    @Override
    public void removeEdge(String fromVertex, String toVertex) {
        if (vertexIndexMap.containsKey(fromVertex) && vertexIndexMap.containsKey(toVertex)) {
            int fromIndex = vertexIndexMap.get(fromVertex);
            int toIndex = vertexIndexMap.get(toVertex);
            adjMatrix[fromIndex][toIndex] = false;
        }
    }

    /**
     * соседи.
     */
    @Override
    public List<String> getNeighbors(String vertex) {
        List<String> neighbors = new ArrayList<>();
        if (vertexIndexMap.containsKey(vertex)) {
            int index = vertexIndexMap.get(vertex);
            for (int i = 0; i < vertexCount; i++) {
                if (adjMatrix[index][i]) {
                    neighbors.add(vertices[i]);
                }
            }
        }
        return neighbors;
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
        StringBuilder sb = new StringBuilder();
        sb.append("Vertices: ");
        for (int i = 0; i < vertexCount; i++) {
            if (vertices[i] != null) {
                sb.append(vertices[i]).append(" ");
            }
        }
        sb.append("\nAdjacency Matrix:\n");
        for (int i = 0; i < vertexCount; i++) {
            if (vertices[i] != null) {
                for (int j = 0; j < vertexCount; j++) {
                    if (vertices[j] != null) {
                        sb.append(adjMatrix[i][j] ? 1 : 0).append(" ");
                    }
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * сортировка.
     */
    public List<String> topologicalSort() {
        List<String> result = new ArrayList<>();
        boolean[] visited = new boolean[vertexCount];
        Stack<String> stack = new Stack<>();

        for (int i = 0; i < vertexCount; i++) {
            if (!visited[i]) {
                topologicalSortUtil(i, visited, stack);
            }
        }

        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        return result;
    }

    /**
     * для сортировки.
     */
    private void topologicalSortUtil(int v, boolean[] visited, Stack<String> stack) {
        visited[v] = true;

        for (int i = 0; i < vertexCount; i++) {
            if (adjMatrix[v][i] && !visited[i]) {
                topologicalSortUtil(i, visited, stack);
            }
        }
        stack.push(vertices[v]);
    }
}