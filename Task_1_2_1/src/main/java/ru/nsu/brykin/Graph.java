package ru.nsu.brykin;

import java.util.ArrayList;
import java.util.List;

/**
 * граф.
 */
interface Graph<T> {
    /**
     * вершина+.
     */
    void addVertex(Vertex<T> vertex);

    /**
     * вершина-.
     */
    void removeVertex(Vertex<T> vertex);

    /**
     * ребро+.
     */
    void addEdge(Vertex<T> fromVertex, Vertex<T> toVertex);

    /**
     * ребро-.
     */
    void removeEdge(Vertex<T> fromVertex, Vertex<T> toVertex);

    /**
     * соседи.
     */
    List<Vertex<T>> getNeighbors(Vertex<T> vertex);

    /**
     * из файла.
     */
    void readFromFile(String fileName);

    /**
     * toString.
     */
    String toString();

    /**
     * вершина1.
     */
    Vertex<T> HeadV();

    /**
     * все вершины.
     */
    ArrayList<Vertex<T>> getAllVertices();
}
