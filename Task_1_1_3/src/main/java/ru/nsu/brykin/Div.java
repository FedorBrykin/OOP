package ru.nsu.brykin;

import java.util.HashMap;

/**
 * класс деления.
 */
class Div extends Expression {
    private final Expression numerator;
    private final Expression denominator;

    /**
     * this.
     */
    public Div(Expression numerator, Expression denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    /**
     * вывод.
     */
    public String toString() {
        return "(" + numerator.toString() + "/" + denominator.toString() + ")";
    }

    /**
     * произ.
     */
    public Expression derivative(String variable) {
        return new Div(
                new Sub(
                        new Mul(numerator.derivative(variable), denominator),
                        new Mul(numerator, denominator.derivative(variable))
                ),
                new Mul(denominator, denominator)
        );
    }

    /**
     * деление.
     */
    public double evaluate(HashMap<String, Double> dict) {
        return numerator.evaluate(dict) / denominator.evaluate(dict);
    }
}
