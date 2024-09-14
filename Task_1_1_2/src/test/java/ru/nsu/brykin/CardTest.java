package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CardTest {
    Card card = new Card("Пик", "Король");

    @Test
    public void testGetValue() {
        assertEquals(10, card.getValue());
    }

    @Test
    public void testToString() {
        assertEquals("Король Пик", card.toString());
    }
}