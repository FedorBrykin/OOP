package ru.nsu.brykin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * сортировка.
 */
public class TopologicalSort<T> {
    /**
     * сорт.
     */
    public List<Vertex<T>> sort(Graph<T> graph) {
        List<Vertex<T>> sortedList = new ArrayList<>();
        Map<Vertex<T>, Integer> inDegreeMap = new HashMap<>();
        for (Vertex<T> vertex : graph.getAllVertices()) {
            inDegreeMap.put(vertex, 0);
        }
        for (Vertex<T> vertex : graph.getAllVertices()) {
            for (Vertex<T> neighbor : graph.getNeighbors(vertex)) {
                inDegreeMap.put(neighbor, inDegreeMap.get(neighbor) + 1);
            }
        }
        Queue<Vertex<T>> zeroInDegreeQueue = new LinkedList<>();
        for (Map.Entry<Vertex<T>, Integer> entry : inDegreeMap.entrySet()) {
            if (entry.getValue() == 0) {
                zeroInDegreeQueue.add(entry.getKey());
            }
        }
        while (!zeroInDegreeQueue.isEmpty()) {
            Vertex<T> current = zeroInDegreeQueue.poll();
            sortedList.add(current);
            for (Vertex<T> neighbor : graph.getNeighbors(current)) {
                inDegreeMap.put(neighbor, inDegreeMap.get(neighbor) - 1);
                if (inDegreeMap.get(neighbor) == 0) {
                    zeroInDegreeQueue.add(neighbor);
                }
            }
        }
        if (sortedList.size() != graph.getAllVertices().size()) {
            throw new IllegalStateException("Graph has at least one cycle.");
        }
        return sortedList;
    }
}
