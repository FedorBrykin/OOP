package ru.nsu.brykin;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    void addTest() {
        Expression e = new Add(new Number(3), new Mul(new Number(2),
                new Variable("x")));
        e.print();

        Expression de = e.derivative("x");
        de.print();

        assertEquals(23.0, e.eval("x = 10; y = 13"));
    }

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
    public void parceTest() {
        Parser parser = new Parser();
        ByteArrayInputStream in = new ByteArrayInputStream("0\n".getBytes());
        InputStream inputStream = System.in;
        System.setIn(in);
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));
        Expression e = parser.parse("(3+(2*x))");
        final String standardOutput = e.print();;
        assertTrue(standardOutput.contains("(3+(2*x))"));
    }

    @Test
    public void parceTest2() {
        Parser parser = new Parser();
        ByteArrayInputStream in = new ByteArrayInputStream("0\n".getBytes());
        InputStream inputStream = System.in;
        System.setIn(in);
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));
        Expression e = parser.parse("(3+(2*x))");
        Expression de = e.derivative("x");
        final String standardOutput = de.print();;
        assertTrue(standardOutput.contains("(0+((0*x)+(2*1)))"));
    }
}