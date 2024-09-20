package ru.nsu.brykin;

import java.util.HashMap;

/**
 * цифры.
 */
class Number extends Expression {
    private final int value;

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
}
