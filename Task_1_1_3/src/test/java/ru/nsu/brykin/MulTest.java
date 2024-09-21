package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import java.util.HashMap;

class MulTest {
    @Test
    void mulTest() {
        Expression e = new Mul(new Number(42), new Div(new Number(100),
                new Variable("x")));
        e.print();

        Expression de = e.derivative("x");
        de.print();

        assertEquals(168.0, e.eval("x = 25"));
    }

    @Test
    void mulZero() {
        Expression e = new Mul(new Variable("x"), new Sub(new Number(3), new Number(3)));
        Expression de = e.derivative("x");
        e.print();
        de.print();
        assertEquals(0.0, e.eval("x = 25"));
    }

    @Test
    void testToString() {
        Expression left = new Number(2);
        Expression right = new Number(3);
        Mul multiplication = new Mul(left, right);
        assertEquals("(2*3)", multiplication.toString());
    }
    
    @Test
    void testDerivative() {
        Expression left = new Variable("x");
        Expression right = new Number(5);
        Mul multiplication = new Mul(left, right);
        Expression result = multiplication.derivative("x");
        assertEquals("((1*5)+(x*0))", result.toString());
    }
    
    @Test
    void testEvaluate() {
        HashMap<String, Double> dict = new HashMap<>();
        dict.put("x", 4.0);
        dict.put("y", 5.0);

        Expression left = new Variable("x");
        Expression right = new Variable("y");
        Mul multiplication = new Mul(left, right);
        double result = multiplication.evaluate(dict);
        assertEquals(20.0, result);
    }
    
    @Test
    void testEvaluateWithZero() {
        HashMap<String, Double> dict = new HashMap<>();
        dict.put("x", 0.0);

        Expression left = new Variable("x");
        Expression right = new Number(10);
        Mul multiplication = new Mul(left, right);
        double result = multiplication.evaluate(dict);
        assertEquals(0.0, result);
    }
    
    @Test
    void testEvaluateWithNegativeValue() {
        HashMap<String, Double> dict = new HashMap<>();
        dict.put("x", -3.0);
        dict.put("y", 6.0);

        Expression left = new Variable("x");
        Expression right = new Variable("y");
        Mul multiplication = new Mul(left, right);
        double result = multiplication.evaluate(dict);
        assertEquals(-18.0, result);
    }
}