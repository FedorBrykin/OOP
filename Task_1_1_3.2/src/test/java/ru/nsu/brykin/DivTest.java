package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void testDerivative() {
        Expression numerator = new Variable("x");
        Expression denominator = new Number(2);
        Div division = new Div(numerator, denominator);
        Expression result = division.derivative("x");
        assertEquals("(((1*2)-(x*0))/(2*2))", result.toString());
    }

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

    @Test
    void testEvaluateWithNegativeValue() {
        HashMap<String, Double> dict = new HashMap<>();
        dict.put("x", -10.0);
        dict.put("y", 5.0);

        Expression numerator = new Variable("x");
        Expression denominator = new Variable("y");
        Div division = new Div(numerator, denominator);
        double result = division.evaluate(dict);
        assertEquals(-2.0, result);
    }

    @Test
    void testDivSimplify() {
        Expression expr = new Div(new Number(6), new Number(3));
        assertEquals(new Number(2), expr.simplify());
    }

    @Test
    void testDivSimplifyDivByZero() {
        Expression expr = new Div(new Number(6), new Number(0));
        assertThrows(ArithmeticException.class, expr::simplify);
    }
}