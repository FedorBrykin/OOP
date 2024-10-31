package ru.nsu.brykin;

import java.util.HashMap;

/**
 * цифры.
 */
class Number extends Expression {
    final int value;

    /**
     * value.
     */
    public Number(int value) {
        this.value = value;
    }

    /**
     * вывод.
     */
    public String toString() {
        return Integer.toString(value);
    }

    /**
     * производная.
     */
    public Expression derivative(String variable) {
        return new Number(0);
    }

    /**
     * возврат значения.
     */
    public double evaluate(HashMap<String, Double> dict) {
        return value;
    }

    public Expression simplify() {
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Number)) {
            return false;
        }
        Number other = (Number) obj;
        return Double.compare(this.value, other.value) == 0;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }
}
