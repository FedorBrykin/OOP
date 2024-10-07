package ru.nsu.brykin;

import java.util.Objects;

/**
 * вершины.
 */
public class Vertex<T> {
    private final T name;

    /**
     * вершины.
     */
    public Vertex(T name) {
        this.name = name;
    }

    /**
     * name.
     */
    public T getName() {
        return name;
    }

    /**
     * ==.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Vertex<?> vertex = (Vertex<?>) obj;
        return Objects.equals(name, vertex.name);
    }

    /**
     * hashcode.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * toString.
     */
    @Override
    public String toString() {
        return name.toString();
    }
}

