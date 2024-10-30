package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import org.junit.jupiter.api.Test;

class DivTest {
    @Test
    void testToString() {
        Expression numerator = new Number(6);
        Expression denominator = new Number(2);
        Div division = new Div(numerator, denominator);
        assertEquals("(6/2)", division.toString());
    }

    // Тестируем метод derivative
    @Test
    void testDerivative() {
        Expression numerator = new Variable("x");
        Expression denominator = new Number(2);
        Div division = new Div(numerator, denominator);
        Expression result = division.derivative("x");
        // Производная от x / 2 равна 1 / 2
        assertEquals("(((1*2)-(x*0))/(2*2))", result.toString());
    }

    // Тестируем метод evaluate
    @Test
    void testEvaluate() {
        HashMap<String, Double> dict = new HashMap<>();
        dict.put("x", 12.0);
        dict.put("y", 3.0);

        Expression numerator = new Variable("x");
        Expression denominator = new Variable("y");
        Div division = new Div(numerator, denominator);
        double result = division.evaluate(dict);
        assertEquals(4.0, result);
    }

    // Тестируем случай деления на ноль
    @Test
    void testEvaluateDivisionByZero() {
        HashMap<String, Double> dict = new HashMap<>();
        dict.put("x", 5.0);
        dict.put("y", 0.0);

        Expression numerator = new Variable("x");
        Expression denominator = new Variable("y");
        Div division = new Div(numerator, denominator);
        double result = division.evaluate(dict);
        assertEquals((double) 5 / 0, result);
    }

    // Тестируем разные значения
    @Test
    void testEvaluateWithNegativeValue() {
        HashMap<String, Double> dict = new HashMap<>();
        dict.put("x", -10.0);
        dict.put("y", 5.0);

        Expression numerator = new Variable("x");
        Expression denominator = new Variable("y");
        Div division = new Div(numerator, denominator);
        double result = division.evaluate(dict);
        assertEquals(-2.0, result); // -10 / 5 = -2
    }

    @Test
    void testDivSimplify() {
        Expression expr = new Div(new Number(6), new Number(3));
        assertEquals(new Number(2), expr.simplify());
    }
}