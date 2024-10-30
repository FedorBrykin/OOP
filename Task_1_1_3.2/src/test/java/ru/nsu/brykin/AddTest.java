package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import java.util.HashMap;

class AddTest {
    @Test
    void addTest() {
        Expression e = new Sub(new Div(new Add(new Number(2), new Mul(new Number(2),
                new Variable("x"))), new Number(2)), new Number(10));

        assertEquals(1.0, e.eval("x = 10; y = 13"));
    }

    @Test
    void testToString() {
        Expression left = new Number(2);
        Expression right = new Number(3);
        Add addition = new Add(left, right);
        assertEquals("(2+3)", addition.toString());
    }

    @Test
    void testDerivative() {
        Expression left = new Variable("x");
        Expression right = new Number(5);
        Add addition = new Add(left, right);
        Expression result = addition.derivative("x");
        assertEquals("(1+0)", result.toString());
    }

    @Test
    void testEvaluate() {
        HashMap<String, Double> dict = new HashMap<>();
        dict.put("x", 5.0);

        Expression left = new Variable("x");
        Expression right = new Number(10);
        Add addition = new Add(left, right);
        double result = addition.evaluate(dict);
        assertEquals(15.0, result);
    }

    @Test
    void testEvaluateZero() {
        HashMap<String, Double> dict = new HashMap<>();
        dict.put("x", 0.0);

        Expression left = new Variable("x");
        Expression right = new Number(7);
        Add addition = new Add(left, right);
        double result = addition.evaluate(dict);
        assertEquals(7.0, result);
    }

    @Test
    void testAddSimplify() {
        Expression expr = new Add(new Number(5), new Number(3));
        assertEquals(new Number(8), expr.simplify());
    }

    @Test
    void testAddSimplifyWithZero() {
        Expression expr = new Add(new Number(0), new Variable("x"));
        assertEquals(new Variable("x"), expr.simplify());
    }

}