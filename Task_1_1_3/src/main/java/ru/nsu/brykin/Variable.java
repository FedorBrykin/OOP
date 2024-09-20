package ru.nsu.brykin;

import java.util.HashMap;

/**
 * класс переменных.
 */
class Variable extends Expression {
    private final String name;

    /**
     * переменные.
     */
    public Variable(String name) {
        this.name = name;
    }

    /**
     * вывод.
     */
    public String toString() {
        return name;
    }

    /**
     * deriv.
     */
    public Expression derivative(String variable) {
        return new Number(name.equals(variable) ? 1 : 0);
    }

    /**
     * eval.
     */
    public double evaluate(HashMap<String, Double> dict) {
        return dict.getOrDefault(name, 0.0);
    }
}