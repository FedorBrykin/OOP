package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


class BlackjackGameTest {
    private Card card = new Card("Пик", "Туз", 11);
    private Deck deck = new Deck();
    private Player player = new Player();
    private Player dealer = new Player();
    private BlackjackGame game = new BlackjackGame();

    @Test
    public void testPlayRoundPlayerWins() {
        // Установка начальных карт
        player.addCard(new Card("Пик", "Туз", 11));
        player.addCard(new Card("Черви", "Девятка", 9));
        dealer.addCard(new Card("Буби", "Шестёрка", 6));
        dealer.addCard(new Card("Крести", "Двойка", 2));

        // Запуск раунда
        game.playRound();

        // Проверка результатов
        assertEquals(1, game.getPlayerWins());
        assertEquals(0, game.getDealerWins());
    }

    @Test
    public void testPlayRoundDealerWins() {
        // Установка начальных карт
        player.addCard(new Card("Пик", "Девятка", 9));
        dealer.addCard(new Card("Буби", "Туз", 11));
        dealer.addCard(new Card("Черви", "Восьмёрка", 8));

        // Запуск раунда
        game.playRound();

        // Проверка результатов
        assertEquals(0, game.getPlayerWins());
        assertEquals(1, game.getDealerWins());
    }

    @Test
    public void testPlayRoundBlackjackPlayer() {
        // Установка карт для блэкджека игрока
        player.addCard(new Card("Пик", "Туз", 11));
        player.addCard(new Card("Черви", "Король", 10));

        // Запуск раунда
        game.playRound();

        // Проверка результатов
        assertEquals(1, game.getPlayerWins());
    }

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