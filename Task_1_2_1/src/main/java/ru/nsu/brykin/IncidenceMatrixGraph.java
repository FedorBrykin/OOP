package ru.nsu.brykin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * матрица инцидентности.
 */
public class IncidenceMatrixGraph implements Graph {
    private List<Vertex> vertices;
    private Map<Vertex, Integer> vertexIndex;
    private int[][] incidenceMatrix;
    private int edgeCount;
    private String headVertex = "";

    /**
     * матрица инцидентности.
     */
    public IncidenceMatrixGraph() {
        vertices = new ArrayList<>();
        vertexIndex = new HashMap<>();
        incidenceMatrix = new int[0][0];
        edgeCount = 0;
    }

    /**
     * вершина+.
     */
    @Override
    public void addVertex(String vertexName) {
        Vertex vertex = new Vertex(vertexName);
        if (!vertexIndex.containsKey(vertex)) {
            if (vertices.isEmpty()) {//                 ПРОВЕРИТЬ
                headVertex = String.valueOf(vertex);
            }
            vertices.add(vertex);
            vertexIndex.put(vertex, vertices.size() - 1);
            int[][] newMatrix = new int[vertices.size()][edgeCount];
            for (int i = 0; i < incidenceMatrix.length; i++) {
                System.arraycopy(incidenceMatrix[i], 0, newMatrix[i], 0, incidenceMatrix[i].length);
            }
            incidenceMatrix = newMatrix;
        }
    }

    /**
     * вершина-.
     */
    @Override
    public void removeVertex(String vertexName) {
        Vertex vertex = new Vertex(vertexName);
        if (vertexIndex.containsKey(vertex)) {
            int index = vertexIndex.remove(vertex);
            vertices.remove(index);
            if (vertices.isEmpty()) {//                 ПРОВЕРИТЬ
                headVertex = String.valueOf(vertex);
            }
            for (int i = index; i < vertices.size(); i++) {
                vertexIndex.put(vertices.get(i), i);
            }
            int[][] newMatrix = new int[vertices.size()][edgeCount];
            for (int i = 0; i < index; i++) {
                newMatrix[i] = incidenceMatrix[i];
            }
            for (int i = index; i < vertices.size(); i++) {
                newMatrix[i] = incidenceMatrix[i + 1];
            }
            incidenceMatrix = newMatrix;
        }
    }

    /**
     * ребро+.
     */
    @Override
    public void addEdge(String fromVertexName, String toVertexName) {
        Vertex fromVertex = new Vertex(fromVertexName);
        Vertex toVertex = new Vertex(toVertexName);
        addVertex(fromVertexName);
        addVertex(toVertexName);
        int fromIndex = vertexIndex.get(fromVertex);
        int toIndex = vertexIndex.get(toVertex);
        int[][] newMatrix = new int[vertices.size()][edgeCount + 1];
        for (int i = 0; i < vertices.size(); i++) {
            System.arraycopy(incidenceMatrix[i], 0, newMatrix[i], 0, incidenceMatrix[i].length);
        }
        newMatrix[fromIndex][edgeCount] = 1;
        newMatrix[toIndex][edgeCount] = -1;
        incidenceMatrix = newMatrix;
        edgeCount++;
    }

    /**
     * ребро-.
     */
    @Override
    public void removeEdge(String fromVertexName, String toVertexName) {
        Vertex fromVertex = new Vertex(fromVertexName);
        Vertex toVertex = new Vertex(toVertexName);
        if (vertexIndex.containsKey(fromVertex) && vertexIndex.containsKey(toVertex)) {
            int fromIdx = vertexIndex.get(fromVertex);
            int toIdx = vertexIndex.get(toVertex);
            for (int j = 0; j < edgeCount; j++) {
                if (incidenceMatrix[fromIdx][j] == 1 && incidenceMatrix[toIdx][j] == -1) {
                    for (int i = 0; i < vertices.size(); i++) {
                        incidenceMatrix[i][j] = 0;
                    }
                    break;
                }
            }
        }
    }


    /**
     * соседи.
     */
    @Override
    public List<String> getNeighbors(String vertexName) {
        List<String> neighbors = new ArrayList<>();
        Vertex vertex = new Vertex(vertexName);
        if (vertexIndex.containsKey(vertex)) {
            int vertexIdx = vertexIndex.get(vertex);
            for (int i = 0; i < incidenceMatrix[vertexIdx].length; i++) {
                if (incidenceMatrix[vertexIdx][i] == 1) {
                    for (int j = 0; j < incidenceMatrix.length; j++) {
                        if (incidenceMatrix[j][i] == -1) {
                            neighbors.add(vertices.get(j).getName());
                        }
                    }
                }
            }
        }
        return neighbors;
    }

    /**
     * из файла.
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
        for (Vertex v : vertices) {
            sb.append(v.getName()).append(" ");
        }
        sb.append("\nIncidence Matrix:\n");
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < edgeCount; j++) {
                sb.append(incidenceMatrix[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * все вершины.
     */
    public ArrayList<Vertex> getAllVertices() {
        return new ArrayList<>(vertices);
    }

    /**
     * вершина1.
     */
    public String HeadV() {
        return headVertex;
    }
}
