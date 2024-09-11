package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.List;


class PlayerTest {
    Player player = new Player();
    Card card = new Card("Пики", "Туз", 11);

    @Test
    public void testAddCard() {
        player.addCard(card);
        List<Card> hand = player.getHand();
        assertEquals(1, hand.size());
        assertTrue(hand.contains(card));
    }

    @Test
    public void testClearHand() {
        List<Card> hand = player.getHand();
        assertEquals(0, hand.size());
    }

    @Test
    public void testGetScore() {
        player.addCard(card);
        List<Card> hand = player.getHand();
        assertEquals(11, player.getScore());
    }
}