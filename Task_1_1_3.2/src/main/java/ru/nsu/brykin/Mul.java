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

    public Expression simplify() {
        Expression simplifiedLeft = left.simplify();
        Expression simplifiedRight = right.simplify();

        if (simplifiedLeft instanceof Number && simplifiedRight instanceof Number) {
            return new Number((int) simplifiedLeft.evaluate(new HashMap<>())
                    * (int) simplifiedRight.evaluate(new HashMap<>()));
        }
        if (simplifiedLeft instanceof Number && ((Number) simplifiedLeft).value == 0) {
            return new Number(0); // a * 0 = 0
        }
        if (simplifiedRight instanceof Number && ((Number) simplifiedRight).value == 0) {
            return new Number(0); // 0 * a = 0
        }
        if (simplifiedLeft instanceof Number && ((Number) simplifiedLeft).value == 1) {
            return simplifiedRight; // 1 * a = a
        }
        if (simplifiedRight instanceof Number && ((Number) simplifiedRight).value == 1) {
            return simplifiedLeft; // a * 1 = a
        }
        return new Mul(simplifiedLeft, simplifiedRight); // возвращаем новое выражение
    }
}
