package ru.nsu.brykin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class AdjacencyListGraph<T> implements Graph<T> {
    private final Map<Vertex<T>, List<Vertex<T>>> adjList = new HashMap<>();

    @Override
    public void addVertex(Vertex<T> vertex) {
        adjList.putIfAbsent(vertex, new ArrayList<>());
    }

    @Override
    public void removeVertex(Vertex<T> vertex) {
        adjList.remove(vertex);
        for (List<Vertex<T>> neighbors : adjList.values()) {
            neighbors.remove(vertex);
        }
    }

    @Override
    public void addEdge(Vertex<T> fromVertex, Vertex<T> toVertex) {
        addVertex(fromVertex);
        addVertex(toVertex);
        adjList.get(fromVertex).add(toVertex);
    }

    @Override
    public void removeEdge(Vertex<T> fromVertex, Vertex<T> toVertex) {
        List<Vertex<T>> neighbors = adjList.get(fromVertex);
        if (neighbors != null) {
            neighbors.remove(toVertex);
        }
    }

    @Override
    public List<Vertex<T>> getNeighbors(Vertex<T> vertex) {
        return adjList.getOrDefault(vertex, new ArrayList<>());
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
        return adjList.toString();
    }

    @Override
    public Vertex<T> HeadV() {
        return adjList.keySet().iterator().next();
    }

    @Override
    public ArrayList<Vertex<T>> getAllVertices() {
        return new ArrayList<>(adjList.keySet());
    }
}
