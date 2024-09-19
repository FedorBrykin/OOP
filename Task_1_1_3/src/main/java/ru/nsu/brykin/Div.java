package ru.nsu.brykin;

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
    public String print() {
        return "(" + numerator.print() + "/" + denominator.print() + ")";
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
    public int eval(String variableAssignments) {
        return numerator.eval(variableAssignments) / denominator.eval(variableAssignments);
    }
}
