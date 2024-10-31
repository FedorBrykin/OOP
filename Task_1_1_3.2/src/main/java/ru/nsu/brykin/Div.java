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

    public Expression simplify() {
        Expression simplifiedNumerator = numerator.simplify();
        Expression simplifiedDenominator = denominator.simplify();

        if (simplifiedDenominator instanceof Number
                && ((Number) simplifiedDenominator).value == 0) {
            throw new ArithmeticException("Division by zero"); // Обработка деления на 0
        }
        if (simplifiedNumerator instanceof Number && simplifiedDenominator instanceof Number) {
            return new Number((int) simplifiedNumerator.evaluate(new HashMap<>())
                    / (int) simplifiedDenominator.evaluate(new HashMap<>()));
        }

        return new Div(simplifiedNumerator, simplifiedDenominator); // возвращаем новое выражение
    }
}
