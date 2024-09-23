package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class MainTest {
    @Test
    public void parceTest() {
        Parser parser = new Parser();
        Expression e = parser.parse("(((2+(2*x))/2)-10)");
        final String standardOutput = e.toString();
        assertTrue(standardOutput.contains("(((2+(2*x))/2)-10)"));
    }

    @Test
    public void parceTest2() {
        Parser parser = new Parser();
        Expression e = parser.parse("(((2+(2*x))/2)-10)");
        Expression de = e.derivative("x");
        final String standardOutput = de.toString();
        assertTrue(standardOutput.contains("(((((0+((0*x)+(2*1)))*2)-((2+(2*x))*0))/(2*2))-0)"));
    }

    @Test
    public void parceTest3() {
        Parser parser = new Parser();
        Expression e = parser.parse("( 3 + ( 2 * x ) )");
        final String standardOutput = e.toString();
        assertTrue(standardOutput.contains("(3+(2*x))"));
    }
}