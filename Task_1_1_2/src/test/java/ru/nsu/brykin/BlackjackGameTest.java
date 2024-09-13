package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class BlackjackGameTest {
    private Card card = new Card("Пик", "Король");
    private Deck deck = new Deck();
    private PlayersHand player = new PlayersHand();
    private PlayersHand dealer = new PlayersHand();
    private BlackjackGame game = new BlackjackGame();

    @Test
    void mainCheck() {
        BlackjackGame.main(null);
        assertTrue(true);
    }

    @Test
    public void testCardCreation() {
        assertEquals("Король Пик", card.toString());
    }

    @Test
    public void testDeckCreation() {
        assertEquals(52, deck.numbers()); // Проверяем, что в колоде 52 карты
    }

    @Test
    public void testDrawCard() {
        Card drawnCard = deck.drawCard();
        assertEquals(51, deck.numbers()); // После вытаскивания одной карты должно остаться 51
    }

    @Test
    public void testPlayerAddCard() {
        player.addCard(card);
        assertEquals(1, player.getSize());
        assertEquals(10, player.getScore());
    }

    @Test
    public void testPlayerScoreOver21() {
        player.addCard(new Card("Черви", "Туз"));
        player.addCard(new Card("Черви", "Десятка"));
        player.addCard(new Card("Черви", "Девятка"));
        assertEquals(20, player.getScore());
    }

}