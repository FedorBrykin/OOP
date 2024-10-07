package ru.nsu.brykin;

/**
 * ребра.
 */
public class Edge<T> {
    private final Vertex<T> from;
    private final Vertex<T> to;

    /**
     *ребра.
     */
    public Edge(Vertex<T> from, Vertex<T> to) {
        this.from = from;
        this.to = to;
    }

    /**
     * из.
     */
    public Vertex<T> getFrom() {
        return from;
    }

    /**
     * в.
     */
    public Vertex<T> getTo() {
        return to;
    }
}
