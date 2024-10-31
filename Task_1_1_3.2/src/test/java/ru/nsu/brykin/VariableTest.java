package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import org.junit.jupiter.api.Test;

class VariableTest {
    @Test
    void testToString() {
        Variable variable = new Variable("x");
        assertEquals("x", variable.toString());
    }

    @Test
    void testDerivative() {
        Variable variableX = new Variable("x");
        Variable variableY = new Variable("y");

        Expression derivativeOfX = variableX.derivative("x");
        Expression derivativeOfY = variableY.derivative("x");

        // Производная от x по x должна быть 1
        assertEquals("1", derivativeOfX.toString());
        // Производная от y по x должна быть 0
        assertEquals("0", derivativeOfY.toString());
    }

    @Test
    void testEvaluate() {
        HashMap<String, Double> dict = new HashMap<>();
        dict.put("x", 10.0);
        dict.put("y", 5.0);

        Variable variableX = new Variable("x");
        Variable variableY = new Variable("y");
        Variable variableZ = new Variable("z"); // переменная, которой нет в словаре

        // Ожидаем 10.0 для x
        assertEquals(10.0, variableX.evaluate(dict));
        // Ожидаем 5.0 для y
        assertEquals(5.0, variableY.evaluate(dict));
        // Ожидаем 0.0 для z, так как ее нет в словаре
        assertEquals(0.0, variableZ.evaluate(dict));
    }
}