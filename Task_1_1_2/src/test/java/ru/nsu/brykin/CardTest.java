package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CardTest {
    Card card = new Card("Пик", "Туз", 11);

    @Test
    public void testGetValue() {
        assertEquals(11, card.getValue());
    }

    @Test
    public void testToString() {
        assertEquals("Туз Пик", card.toString());
    }
}