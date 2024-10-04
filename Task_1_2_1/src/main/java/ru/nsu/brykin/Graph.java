package ru.nsu.brykin;

import java.util.List;

/**
 * граф.
 */
interface Graph<T> {
    /**
     * вершина+.
     */
    void addVertex(String vertex);

    /**
     * вершина-.
     */
    void removeVertex(String vertex);

    /**
     * ребро+.
     */
    void addEdge(String fromVertex, String toVertex);

    /**
     * ребро-.
     */
    void removeEdge(String fromVertex, String toVertex);

    /**
     * соседи.
     */
    List<String> getNeighbors(String vertex);

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
    String HeadV();
}
