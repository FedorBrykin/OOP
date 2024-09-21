package ru.nsu.brykin;

import java.util.HashMap;

/**
 * исходный класс.
 */
abstract class Expression {
    public double eval(String s) {
        return evaluate(parseAssignments(s));
    }

    /**
     * и ещё парсинг).
     */
    private HashMap<String, Double> parseAssignments(String variableAssignments) {
        HashMap<String, Double> vars = new HashMap<>();
        String[] assignments = variableAssignments.split(";");
        for (String assignment : assignments) {
            String[] parts = assignment.split("=");
            if (parts.length == 2) {
                String varName = parts[0].trim();
                double value = Integer.parseInt(parts[1].trim());
                vars.put(varName, value);
            }
        }
        return vars;
    }

    abstract protected double evaluate(HashMap<String, Double> dict);

    /**
     * вывод.
     */
    public void print() {
        System.out.println(this);
    }

    /**
     * диффер.
     */
    public abstract Expression derivative(String variable);
}