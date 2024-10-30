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

    public Expression simplify() {
        Expression simplifiedLeft = left.simplify();
        Expression simplifiedRight = right.simplify();

        if (simplifiedLeft instanceof Number && simplifiedRight instanceof Number) {
            return new Number((int) simplifiedLeft.evaluate(new HashMap<>()) + (int) simplifiedRight.evaluate(new HashMap<>()));
        }
        if (simplifiedLeft instanceof Number && ((Number) simplifiedLeft).value == 0) {
            return simplifiedRight; // a + 0 = a
        }
        if (simplifiedRight instanceof Number && ((Number) simplifiedRight).value == 0) {
            return simplifiedLeft; // 0 + a = a
        }

        return new Add(simplifiedLeft, simplifiedRight); // возвращаем новое выражение
    }
}
