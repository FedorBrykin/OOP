package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AddTest {
    @Test
    void addTest() {
        Expression e = new Add(new Number(3), new Mul(new Number(2),
                new Variable("x")));

        Expression de = e.derivative("x");
        de.print();

        assertEquals(23.0, e.eval("x = 10; y = 13"));
    }
}