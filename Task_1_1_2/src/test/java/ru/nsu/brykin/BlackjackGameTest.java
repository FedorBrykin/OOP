package ru.nsu.brykin;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BlackjackGameTest {
    Card card = new Card("Пик", "Туз", 11);
    Deck deck = new Deck();
    Player player = new Player();
    BlackjackGame game = new BlackjackGame();

    @Test
    public void testCardCreation() {
        assertEquals("Туз Пик", card.toString());
    }
    @Test
    public void testDeckCreation() {
        assertEquals(52, deck.cards.size()); // Проверяем, что в колоде 52 карты
    }

    @Test
    public void testDrawCard() {
        Card drawnCard = deck.drawCard();
        assertNotNull(drawnCard);
        assertEquals(51, deck.cards.size()); // После вытаскивания одной карты должно остаться 51
    }

    @Test
    public void testPlayerAddCard() {
        player.addCard(card);
        assertEquals(1, player.getHand().size());
        assertEquals(11, player.getScore());
    }

    @Test
    public void testPlayerScoreWithoutAces() {
        player.addCard(new Card("Черви", "Десятка", 10));
        player.addCard(new Card("Черви", "Девять", 9));
        assertEquals(19, player.getScore());
    }

    @Test
    public void testPlayerScoreWithAces() {
        player.addCard(new Card("Черви", "Туз", 11));
        player.addCard(new Card("Черви", "Девять", 9));
        assertEquals(20, player.getScore());

        player.addCard(new Card("Черви", "Туз", 11)); // Добавляем еще один туз
        assertEquals(21, player.getScore());
    }

    @Test
    public void testPlayerScoreOver21() {
        player.addCard(new Card("Черви", "Король", 10));
        player.addCard(new Card("Черви", "Десятка", 10));
        player.addCard(new Card("Черви", "Девятка", 9));
        assertEquals(29, player.getScore());
    }

    @Test
    public void testCheckBlackjack() {
        player.addCard(new Card("Пики", "Туз", 11));
        player.addCard(new Card("Черви", "Десятка", 10));
        assertTrue(game.checkBlackjack(player)); // Должно вернуть true для блэкджека
    }

}