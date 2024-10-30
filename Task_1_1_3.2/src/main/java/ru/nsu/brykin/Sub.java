package ru.nsu.brykin;

import java.util.HashMap;

/**
 * класс вычитания.
 */
class Sub extends Expression {
    private final Expression left;
    private final Expression right;

    /**
     * this.
     */
    public Sub(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * вывод.
     */
    public String toString() {
        return "(" + left.toString() + "-" + right.toString() + ")";
    }

    /**
     * произ.
     */
    public Expression derivative(String variable) {
        return new Sub(left.derivative(variable), right.derivative(variable));
    }

    /**
     * вычитание.
     */
    public double evaluate(HashMap<String, Double> dict) {
        return left.evaluate(dict) - right.evaluate(dict);
    }

    public Expression simplify() {
        Expression simplifiedLeft = left.simplify();
        Expression simplifiedRight = right.simplify();

        if (simplifiedLeft instanceof Number && simplifiedRight instanceof Number) {
            return new Number((int) simplifiedLeft.evaluate(new HashMap<>()) - (int) simplifiedRight.evaluate(new HashMap<>()));
        }
        if (simplifiedLeft.equals(simplifiedRight)) {
            return new Number(0); // a - a = 0
        }
        return new Sub(simplifiedLeft, simplifiedRight); // возвращаем новое выражение
    }
}
