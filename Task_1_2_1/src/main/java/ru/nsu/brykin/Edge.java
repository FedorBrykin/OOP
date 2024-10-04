package ru.nsu.brykin;

/**
 * рёбра.
 */
public class Edge {
    private Vertex from;
    private Vertex to;

    /**
     * рёбра.
     */
    public Edge(Vertex from, Vertex to) {
        this.from = from;
        this.to = to;
    }

    /**
     * из.
     */
    public Vertex getFrom() {
        return from;
    }

    /**
     * в.
     */
    public Vertex getTo() {
        return to;
    }

    /**
     * toString.
     */
    @Override
    public String toString() {
        return from + " -> " + to;
    }
}

