package ru.nsu.brykin;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;


/**
 * матрица инцидентности.
 */
public class IncidenceMatrixGraph implements Graph {
    private List<String> vertices;
    Map<String, Integer> vertexIndex;
    int[][] incidenceMatrix;
    int edgeCount;

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
     * добавление вершины.
     */
    @Override
    public void addVertex(String vertex) {
        if (!vertexIndex.containsKey(vertex)) {
            vertices.add(vertex);
            vertexIndex.put(vertex, vertices.size() - 1);
            int[][] newMatrix = new int[vertices.size()][edgeCount];
            for (int i = 0; i < incidenceMatrix.length; i++) {
                System.arraycopy(incidenceMatrix[i], 0, newMatrix[i], 0,
                        incidenceMatrix[i].length);
            }
            incidenceMatrix = newMatrix;
        }
    }

    /**
     * удаление вершины.
     */
    @Override
    public void removeVertex(String vertex) {
        if (vertexIndex.containsKey(vertex)) {
            int index = vertexIndex.remove(vertex);
            vertices.remove(index);
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
     * добавление ребра.
     */
    @Override
    public void addEdge(String fromVertex, String toVertex) {
        addVertex(fromVertex);
        addVertex(toVertex);
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
     * удаление ребра.
     */
    @Override
    public void removeEdge(String fromVertex, String toVertex) {
        if (vertexIndex.containsKey(fromVertex) && vertexIndex.containsKey(toVertex)) {
            int fromIdx = vertexIndex.get(fromVertex);
            int toIdx = vertexIndex.get(toVertex);

            // Найти столбец инцидентности для удаления
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
    public List<String> getNeighbors(String vertex) {
        List<String> neighbors = new ArrayList<>();
        int vertexIdx = vertexIndex.get(vertex);
        for (int i = 0; i < incidenceMatrix[vertexIdx].length; i++) {
            if (incidenceMatrix[vertexIdx][i] == 1) {
                for (int j = 0; j < incidenceMatrix.length; j++) {
                    if (incidenceMatrix[j][i] == -1) {
                        neighbors.add(vertices.get(j));
                    }
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
        sb.append(String.join(",", vertices));
        sb.append("\n");
        for (int[] row : incidenceMatrix) {
            for (int cell : row) {
                sb.append(cell).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * сортировка.
     */
    public List<String> topologicalSort() {
        Map<String, Integer> inDegree = new HashMap<>();
        for (String vertex : vertices) {
            inDegree.put(vertex, 0);
        }
        for (int j = 0; j < edgeCount; j++) {
            for (int i = 0; i < vertices.size(); i++) {
                if (incidenceMatrix[i][j] == -1) {
                    String toVertex = vertices.get(i);
                    inDegree.put(toVertex, inDegree.get(toVertex) + 1);
                }
            }
        }
        Stack<String> stack = new Stack<>();
        for (String vertex : vertices) {
            if (inDegree.get(vertex) == 0) {
                stack.push(vertex);
            }
        }
        List<String> sortedList = new ArrayList<>();
        while (!stack.isEmpty()) {
            String vertex = stack.pop();
            sortedList.add(vertex);
            int vertexIdx = vertexIndex.get(vertex);
            for (int j = 0; j < edgeCount; j++) {
                if (incidenceMatrix[vertexIdx][j] == 1) {
                    for (int i = 0; i < vertices.size(); i++) {
                        if (incidenceMatrix[i][j] == -1) {
                            String toVertex = vertices.get(i);
                            inDegree.put(toVertex, inDegree.get(toVertex) - 1);
                            if (inDegree.get(toVertex) == 0) {
                                stack.push(toVertex);
                            }
                        }
                    }
                }
            }
        }
        if (sortedList.size() != vertices.size()) {
            throw new RuntimeException("Граф содержит циклы!");
        }
        return sortedList;
    }
}
