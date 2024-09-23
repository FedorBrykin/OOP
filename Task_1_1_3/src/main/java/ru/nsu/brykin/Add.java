package ru.nsu.brykin;

import java.util.HashMap;

/**
 * класс сложения.
 */
class Add extends Expression {
    private final Expression left;
    private final Expression right;

    /**
     * this.
     */
    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * вывод.
     */
    @Override
    public String toString() {
        return "(" + left.toString() + "+" + right.toString() + ")";
    }

    /**
     * произ.
     */
    public Expression derivative(String variable) {
        return new Add(left.derivative(variable), right.derivative(variable));
    }

    /**
     * сложение.
     */
    public double evaluate(HashMap<String, Double> dict) {
        return left.evaluate(dict) + right.evaluate(dict);
    }
}
