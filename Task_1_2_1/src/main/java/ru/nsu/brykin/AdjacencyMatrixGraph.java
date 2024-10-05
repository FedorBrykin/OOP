package ru.nsu.brykin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class AdjacencyMatrixGraph<T> implements Graph<T> {
    private final List<Vertex<T>> vertices = new ArrayList<>();
    private final boolean[][] adjacencyMatrix;

    public AdjacencyMatrixGraph(int size) {
        adjacencyMatrix = new boolean[size][size];
    }

    @Override
    public void addVertex(Vertex<T> vertex) {
        if (!vertices.contains(vertex)) {
            vertices.add(vertex);
        }
    }

    @Override
    public void removeVertex(Vertex<T> vertex) {
        int index = vertices.indexOf(vertex);
        if (index >= 0) {
            vertices.remove(index);
            for (int i = 0; i < vertices.size(); i++) {
                adjacencyMatrix[index][i] = false;
                adjacencyMatrix[i][index] = false;
            }
        }
    }

    @Override
    public void addEdge(Vertex<T> fromVertex, Vertex<T> toVertex) {
        int fromIndex = vertices.indexOf(fromVertex);
        int toIndex = vertices.indexOf(toVertex);
        if (fromIndex >= 0 && toIndex >= 0) {
            adjacencyMatrix[fromIndex][toIndex] = true;
        }
    }

    @Override
    public void removeEdge(Vertex<T> fromVertex, Vertex<T> toVertex) {
        int fromIndex = vertices.indexOf(fromVertex);
        int toIndex = vertices.indexOf(toVertex);
        if (fromIndex >= 0 && toIndex >= 0) {
            adjacencyMatrix[fromIndex][toIndex] = false;
        }
    }

    @Override
    public List<Vertex<T>> getNeighbors(Vertex<T> vertex) {
        List<Vertex<T>> neighbors = new ArrayList<>();
        int index = vertices.indexOf(vertex);
        if (index >= 0) {
            for (int i = 0; i < vertices.size(); i++) {
                if (adjacencyMatrix[index][i]) {
                    neighbors.add(vertices.get(i));
                }
            }
        }
        return neighbors;
    }

    @Override
    public void readFromFile(String fileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(fileName));
        int vertexCount = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < vertexCount; i++) {
            addVertex((Vertex<T>) new Vertex<>(i));
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            if (parts.length == 2) {
                Vertex<T> from = (Vertex<T>) new Vertex<>(parts[0]);
                Vertex<T> to = (Vertex<T>) new Vertex<>(parts[1]);
                addEdge(from, to);
            }
        }
    }


    @Override
    public String toString() {
        return Arrays.deepToString(adjacencyMatrix);
    }

    @Override
    public Vertex<T> HeadV() {
        return vertices.isEmpty() ? null : vertices.get(0);
    }

    @Override
    public ArrayList<Vertex<T>> getAllVertices() {
        return new ArrayList<>(vertices);
    }
}
