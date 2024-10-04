package ru.nsu.brykin;

/**
 * вершины.
 */
public class Vertex {
    private String name;

    /**
     * в.
     */
    public Vertex(String name) {
        this.name = name;
    }

    /**
     * имя.
     */
    public String getName() {
        return name;
    }

    /**
     * toString.
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * сравнение.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Vertex)) return false;
        Vertex other = (Vertex) obj;
        return name.equals(other.name);
    }

    /**
     * hashCode.
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}

