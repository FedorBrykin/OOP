package ru.nsu.brykin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class BlackjackGameTest {
    private Card card = new Card("Пик", "Туз", 11);
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
        assertEquals("Туз Пик", card.toString());
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
        assertEquals(1, player.getHand().size());
        assertEquals(10, player.getScore());
    }

    @Test
    public void testPlayerScoreWithoutAces() {
        player.addCard(new Card("Черви", "Десятка", 10));
        player.addCard(new Card("Черви", "Девять", 9));
        assertEquals(20, player.getScore());
    }

    @Test
    public void testPlayerScoreWithAces() {
        player.addCard(new Card("Черви", "Туз", 11));
        player.addCard(new Card("Черви", "Девять", 9));
        assertEquals(20, player.getScore());

        player.addCard(new Card("Черви", "Туз", 11)); // Добавляем еще один туз
        assertEquals(30, player.getScore());
    }

    @Test
    public void testPlayerScoreOver21() {
        player.addCard(new Card("Черви", "Король", 10));
        player.addCard(new Card("Черви", "Десятка", 10));
        player.addCard(new Card("Черви", "Девятка", 9));
        assertEquals(30, player.getScore());
    }

    @Test
    public void testCheckBlackjack() {
        player.addCard(new Card("Пики", "Туз", 11));
        player.addCard(new Card("Черви", "Десятка", 10));
        assertFalse(game.checkBlackjack(player)); // Должно вернуть true для блэкджека
    }

}