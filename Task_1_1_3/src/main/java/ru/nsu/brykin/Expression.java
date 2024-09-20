package ru.nsu.brykin;

/**
 * исходный класс.
 */
abstract class Expression {
    /**
     * вывод.
     */
    public abstract String print();

    /**
     * диффер.
     */
    public abstract Expression derivative(String variable);

    /**
     * значения.
     */
    public abstract int eval(String variableAssignments);
}