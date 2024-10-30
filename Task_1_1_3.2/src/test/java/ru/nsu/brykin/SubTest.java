package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import org.junit.jupiter.api.Test;

class SubTest {
    @Test
    void testToString() {
        Expression left = new Number(5);
        Expression right = new Number(3);
        Sub subtraction = new Sub(left, right);
        assertEquals("(5-3)", subtraction.toString());
    }

    @Test
    void testDerivative() {
        Expression left = new Variable("x");
        Expression right = new Number(4);
        Sub subtraction = new Sub(left, right);
        Expression result = subtraction.derivative("x");
        assertEquals("(1-0)", result.toString());  // производная от x - 4 равна 1 - 0
    }

    @Test
    void testEvaluate() {
        HashMap<String, Double> dict = new HashMap<>();
        dict.put("x", 10.0);

        Expression left = new Variable("x");
        Expression right = new Number(3);
        Sub subtraction = new Sub(left, right);
        double result = subtraction.evaluate(dict);
        assertEquals(7.0, result); // 10 - 3 = 7
    }

    @Test
    void testEvaluateWithZero() {
        HashMap<String, Double> dict = new HashMap<>();
        dict.put("x", 0.0);

        Expression left = new Variable("x");
        Expression right = new Number(1);
        Sub subtraction = new Sub(left, right);
        double result = subtraction.evaluate(dict);
        assertEquals(-1.0, result); // 0 - 1 = -1
    }

    @Test
    void testSubSimplify() {
        Expression expr = new Sub(new Number(5), new Number(3));
        assertEquals(new Number(2), expr.simplify());
    }

    @Test
    void testSubSimplifyWithSameVariables() {
        Expression expr = new Sub(new Variable("x"), new Variable("x"));
        assertEquals(new Number(0), expr.simplify());
    }
}