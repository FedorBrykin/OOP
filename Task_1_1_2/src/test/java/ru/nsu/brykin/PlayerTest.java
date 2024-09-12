package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;


class PlayerTest {
    PlayersHand player = new PlayersHand();
    Card card = new Card("Пики", "Король", 10);

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
        assertEquals(10, player.getScore());
    }
}