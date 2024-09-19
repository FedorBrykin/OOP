package ru.nsu.brykin;

import java.util.HashMap;
import java.util.Map;

/**
 * класс переменных.
 */
class Variable extends Expression {
    private final String name;

    /**
     * переменные.
     */
    public Variable(String name) {
        this.name = name;
    }

    /**
     * вывод.
     */
    public String print() {
        return name;
    }

    /**
     * deriv.
     */
    public Expression derivative(String variable) {
        return new Number(name.equals(variable) ? 1 : 0);
    }

    /**
     * eval.
     */
    public int eval(String variableAssignments) {
        Map<String, Integer> variables = parseAssignments(variableAssignments);
        return variables.getOrDefault(name, 0);
    }

    /**
     * и ещё парсинг).
     */
    private Map<String, Integer> parseAssignments(String variableAssignments) {
        Map<String, Integer> vars = new HashMap<>();
        String[] assignments = variableAssignments.split(";");
        for (String assignment : assignments) {
            String[] parts = assignment.split("=");
            if (parts.length == 2) {
                String varName = parts[0].trim();
                int value = Integer.parseInt(parts[1].trim());
                vars.put(varName, value);
            }
        }
        return vars;
    }
}