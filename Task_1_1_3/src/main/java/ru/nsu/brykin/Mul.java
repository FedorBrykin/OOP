package ru.nsu.brykin;

import java.util.HashMap;

/**
 * класс умножения.
 */
class Mul extends Expression {
    private final Expression left;
    private final Expression right;

    /**
     * this.
     */
    public Mul(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * вывод.
     */
    public String toString() {
        return "(" + left.toString() + "*" + right.toString() + ")";
    }

    /**
     * произ.
     */
    public Expression derivative(String variable) {
        return new Add(new Mul(left.derivative(variable), right),
                new Mul(left, right.derivative(variable)));
    }

    /**
     * умножение.
     */
    public double evaluate(HashMap<String, Double> dict) {
        return left.evaluate(dict) * right.evaluate(dict);
    }
}
