package ru.nsu.brykin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IncidenceMatrixGraph<T> implements Graph<T> {
    private final List<Vertex<T>> vertices = new ArrayList<>();
    private final List<Edge<T>> edges = new ArrayList<>();

    public IncidenceMatrixGraph() {}

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
            edges.removeIf(edge -> edge.getFrom().equals(vertex) || edge.getTo().equals(vertex));
        }
    }

    @Override
    public void addEdge(Vertex<T> fromVertex, Vertex<T> toVertex) {
        addVertex(fromVertex);
        addVertex(toVertex);
        edges.add(new Edge<>(fromVertex, toVertex));
    }

    @Override
    public void removeEdge(Vertex<T> fromVertex, Vertex<T> toVertex) {
        edges.removeIf(edge -> edge.getFrom().equals(fromVertex) && edge.getTo().equals(toVertex));
    }

    @Override
    public List<Vertex<T>> getNeighbors(Vertex<T> vertex) {
        List<Vertex<T>> neighbors = new ArrayList<>();
        for (Edge<T> edge : edges) {
            if (edge.getFrom().equals(vertex)) {
                neighbors.add(edge.getTo());
            } else if (edge.getTo().equals(vertex)) {
                neighbors.add(edge.getFrom());
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
        return edges.toString();
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
