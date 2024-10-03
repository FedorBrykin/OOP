package ru.nsu.brykin;

import java.util.List;

/**
 * интерфейс.
 */
interface Graph {
    /**
     * добавление вершины.
     */
    void addVertex(String vertex);

    /**
     * удаление вершины.
     */
    void removeVertex(String vertex);

    /**
     * добавление ребра.
     */
    void addEdge(String fromVertex, String toVertex);

    /**
     * удаление ребра.
     */
    void removeEdge(String fromVertex, String toVertex);

    /**
     * соседи.
     */
    List<String> getNeighbors(String vertex);

    /**
     * чтение из файла.
     */
    void readFromFile(String fileName);

    /**
     * toString.
     */
    String toString();
}