package ru.nsu.brykin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * список смежности.
 */
public class AdjacencyListGraph implements Graph<Vertex> {
    private Map<Vertex, List<Vertex>> adjList;
    private String headVertex = "";

    /**
     * список смежности.
     */
    public AdjacencyListGraph() {
        adjList = new HashMap<>();
    }

    /**
     * добавление вершины.
     */
    @Override
    public void addVertex(String vertexName) {
        Vertex vertex = new Vertex(vertexName);
        if (!adjList.containsKey(vertex)) {
            if (adjList.isEmpty()) {
                headVertex = String.valueOf(vertex);
            }
            adjList.put(vertex, new ArrayList<>());
        }
    }

    /**
     * удаление вершины.
     */
    @Override
    public void removeVertex(String vertexName) {
        Vertex vertex = new Vertex(vertexName);
        adjList.remove(vertex);
        adjList.values().forEach(e -> e.remove(vertex));
        if (adjList.isEmpty()) {
            headVertex = "";
        }
    }

    /**
     * добавление ребра.
     */
    @Override
    public void addEdge(String fromVertexName, String toVertexName) {
        Vertex fromVertex = new Vertex(fromVertexName);
        Vertex toVertex = new Vertex(toVertexName);
        addVertex(fromVertexName);
        addVertex(toVertexName);
        adjList.get(fromVertex).add(toVertex);
    }

    /**
     * удаление ребра.
     */
    @Override
    public void removeEdge(String fromVertexName, String toVertexName) {
        Vertex fromVertex = new Vertex(fromVertexName);
        Vertex toVertex = new Vertex(toVertexName);
        if (adjList.containsKey(fromVertex)) {
            adjList.get(fromVertex).remove(toVertex);
        }
    }

    /**
     * соседи.
     */
    @Override
    public List<String> getNeighbors(String vertexName) {
        Vertex vertex = new Vertex(vertexName);
        List<Vertex> neighbors = adjList.getOrDefault(vertex, new ArrayList<>());
        List<String> neighborNames = new ArrayList<>();
        for (Vertex neighbor : neighbors) {
            neighborNames.add(neighbor.getName());
        }
        return neighborNames;
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
     * все вершины.
     */
    public ArrayList<Vertex> getAllVertices() {
        return new ArrayList<>(adjList.keySet());
    }

    /**
     * первая вершина.
     */
    public String HeadV() {
        return headVertex;
    }
}
