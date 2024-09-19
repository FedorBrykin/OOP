package ru.nsu.brykin;

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
    public String print() {
        return "(" + left.print() + "*" + right.print() + ")";
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
    public int eval(String variableAssignments) {
        return left.eval(variableAssignments) * right.eval(variableAssignments);
    }
}
