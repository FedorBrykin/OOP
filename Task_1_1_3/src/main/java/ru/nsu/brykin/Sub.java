package ru.nsu.brykin;

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
    public String print() {
        return "(" + left.print() + "-" + right.print() + ")";
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
    public int eval(String variableAssignments) {
        return left.eval(variableAssignments) - right.eval(variableAssignments);
    }
}
