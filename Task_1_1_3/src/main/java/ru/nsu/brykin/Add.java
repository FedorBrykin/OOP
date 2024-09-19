package ru.nsu.brykin;

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
    public String print() {
        return "(" + left.print() + "+" + right.print() + ")";
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
    public int eval(String variableAssignments) {
        return left.eval(variableAssignments) + right.eval(variableAssignments);
    }
}
