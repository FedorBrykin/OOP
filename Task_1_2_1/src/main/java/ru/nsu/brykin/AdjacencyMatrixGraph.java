package ru.nsu.brykin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * матрица смежности.
 */
public class AdjacencyMatrixGraph implements Graph<Vertex> {
    Map<Vertex, Integer> vertexIndexMap;
    private Vertex[] vertices;
    private boolean[][] adjMatrix;
    private int vertexCount;
    private String headVertex = "";

    /**
     * матрица смежности.
     */
    public AdjacencyMatrixGraph(int capacity) {
        vertexIndexMap = new HashMap<>();
        vertices = new Vertex[capacity];
        adjMatrix = new boolean[capacity][capacity];
        vertexCount = 0;
    }

    /**
     * вершина+.
     */
    @Override
    public void addVertex(String vertexName) {
        Vertex vertex = new Vertex(vertexName);
        if (!vertexIndexMap.containsKey(vertex)) {
            if (vertexCount == 0) {
                headVertex = String.valueOf(vertex);
            }
            vertexIndexMap.put(vertex, vertexCount);
            vertices[vertexCount++] = vertex;
        }
    }

    /**
     * вершина-.
     */
    @Override
    public void removeVertex(String vertexName) {
        Vertex vertex = new Vertex(vertexName);
        if (vertexIndexMap.containsKey(vertex)) {
            if (vertexCount == 1) {
                headVertex = "";
            }
            int index = vertexIndexMap.remove(vertex);
            for (int i = 0; i < vertexCount; i++) {
                adjMatrix[i][index] = false; // Удаление всех рёбер
                adjMatrix[index][i] = false;
            }
            vertices[index] = null; // Удаляем вершину из массива
        }
    }

    /**
     * ребро+.
     */
    @Override
    public void addEdge(String fromVertexName, String toVertexName) {
        Vertex fromVertex = new Vertex(fromVertexName);
        Vertex toVertex = new Vertex(toVertexName);
        if (vertexIndexMap.containsKey(fromVertex) && vertexIndexMap.containsKey(toVertex)) {
            int fromIndex = vertexIndexMap.get(fromVertex);
            int toIndex = vertexIndexMap.get(toVertex);
            adjMatrix[fromIndex][toIndex] = true;
        }
    }

    /**
     * ребро-.
     */
    @Override
    public void removeEdge(String fromVertexName, String toVertexName) {
        Vertex fromVertex = new Vertex(fromVertexName);
        Vertex toVertex = new Vertex(toVertexName);
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
    public List<String> getNeighbors(String vertexName) {
        List<String> neighbors = new ArrayList<>();
        Vertex vertex = new Vertex(vertexName);
        if (vertexIndexMap.containsKey(vertex)) {
            int index = vertexIndexMap.get(vertex);
            for (int i = 0; i < vertexCount; i++) {
                if (adjMatrix[index][i]) {
                    neighbors.add(vertices[i].getName());
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
     * все вершины.
     */
    public ArrayList<Vertex> getAllVertices() {
        return new ArrayList<>(List.of(vertices));
    }

    /**
     * первая вершина.
     */
    public String HeadV() {
        return headVertex;
    }
}

