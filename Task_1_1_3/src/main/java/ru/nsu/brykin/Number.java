package ru.nsu.brykin;

/**
 * цифры.
 */
class Number extends Expression {
    private final int value;

    /**
     * value.
     */
    public Number(int value) {
        this.value = value;
    }

    /**
     * вывод.
     */
    public String print() {
        return Integer.toString(value);
    }

    /**
     * производная.
     */
    public Expression derivative(String variable) {
        return new Number(0);
    }

    /**
     * возврат значения.
     */
    public int eval(String variableAssignments) {
        return value;
    }
}
