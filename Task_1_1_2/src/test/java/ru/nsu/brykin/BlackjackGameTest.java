package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class BlackjackGameTest {
    private Card card = new Card("Пик", "Король");
    private Deck deck = new Deck();
    private PlayersHand player = new PlayersHand();
    private PlayersHand dealer = new PlayersHand();
    private BlackjackGame game = new BlackjackGame();

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

    @Test
    void testCheckBlackjackPlayerWins() {
        player.addCard(new Card("Черви", "Туз")); // 11
        player.addCard(new Card("Черви", "Король")); // 10

        assertTrue(game.checkBlackjack(player), "Вы получили блэкджек! ");
    }

    @Test
    void testCheckBlackjackDealerWins() {
        dealer.addCard(new Card("Черви", "Туз")); // 11
        dealer.addCard(new Card("Черви", "Король")); // 10

        assertTrue(game.checkBlackjack(dealer), "Дилер получил блэкджек! ");
    }

    @Test
    void testDealersTurnDealerBusted() {
        PlayersHand dealerHand = new PlayersHand();
        dealerHand.addCard(new Card("Черви", "Девятка")); // 9
        dealerHand.addCard(new Card("Черви", "Восьмёрка")); // 8
        dealerHand.addCard(new Card("Черви", "Пятёрка")); // 5

        game.dealersTurn(new PlayersHand(), dealerHand);

        assertTrue(dealerHand.getScore() > 21, "Вы выиграли раунд!");
    }

    @Test
    void testRoundResultsPlayerWins() {
        PlayersHand playerHand = new PlayersHand();
        PlayersHand dealerHand = new PlayersHand();

        playerHand.addCard(new Card("Черви", "Девятка"));
        playerHand.addCard(new Card("Черви", "Король"));
        dealerHand.addCard(new Card("Черви", "Семёрка"));
        dealerHand.addCard(new Card("Черви", "Пятёрка"));

        game.roundResults(playerHand, dealerHand);
        assertEquals(1, game.getPlayerWins());
        assertEquals(0, game.getDealerWins());
    }

    @Test
    void testRoundResultsDealerWins() {
        PlayersHand playerHand = new PlayersHand();
        PlayersHand dealerHand = new PlayersHand();

        playerHand.addCard(new Card("Черви", "Пятёрка"));
        dealerHand.addCard(new Card("Черви", "Десятка"));
        dealerHand.addCard(new Card("Черви", "Король"));

        game.roundResults(playerHand, dealerHand);
        assertEquals(0, game.getPlayerWins());
        assertEquals(1, game.getDealerWins());
    }

    @Test
    void testRoundResultsDraw() {
        PlayersHand playerHand = new PlayersHand();
        PlayersHand dealerHand = new PlayersHand();

        playerHand.addCard(new Card("Черви", "Десятка"));
        playerHand.addCard(new Card("Черви", "Восьмёрка"));
        dealerHand.addCard(new Card("Черви", "Девятка"));
        dealerHand.addCard(new Card("Черви", "Девятка"));

        game.roundResults(playerHand, dealerHand);
        assertEquals(0, game.getPlayerWins());
        assertEquals(0, game.getDealerWins()); // Проверяем что ничья
    }

    @Test
    void testDealerTurnDrawsCardAndIncreasesScore() {
        PlayersHand dealerHand = new PlayersHand();
        dealerHand.addCard(new Card("Черви", "Шестёрка"));
        dealerHand.addCard(new Card("Черви", "Пятёрка"));

        game.dealersTurn(new PlayersHand(), dealerHand);

        assertTrue(dealerHand.getScore() >= 17); // Проверяем, что дилер достиг 17 очков
    }

    // Проверка, что игрок набирает 21 с 3 картами
    @Test
    void testPlayerReaches21WithThreeCards() {
        PlayersHand playerHand = new PlayersHand();
        playerHand.addCard(new Card("Черви", "Король"));
        playerHand.addCard(new Card("Черви", "Пятёрка"));
        playerHand.addCard(new Card("Черви", "Шестёрка")); // 10 + 5 + 6 = 21

        game.roundResults(playerHand, new PlayersHand());
        assertEquals(1, game.getPlayerWins());
    }

    // Проверка, что нигде не происходит деления на ноль или других ошибок
    @Test
    void testGameHandlesUnexpectedCardValues() {
        PlayersHand playerHand = new PlayersHand();
        PlayersHand dealerHand = new PlayersHand();

        // Добавление карт, которые не должны влиять на логику
        playerHand.addCard(new Card("Черви", "Пятёрка"));
        playerHand.addCard(new Card("Черви", "Пятёрка"));

        dealerHand.addCard(new Card("Черви", "Пятёрка"));
        dealerHand.addCard(new Card("Черви", "Пятёрка"));

        game.roundResults(playerHand, dealerHand);
        assertEquals(0, game.getPlayerWins());
        assertEquals(0, game.getDealerWins());
    }

    @Test
    void testGetPlayerWins() {
        game.getPlayerWins();
        assertEquals(0, game.getPlayerWins());
    }

    @Test
    void testGetDealerWins() {
        game.getDealerWins();
        assertEquals(0, game.getDealerWins());
    }

    @Test
    public void testDraw() {
        ByteArrayInputStream in = new ByteArrayInputStream("0\n".getBytes());
        InputStream inputStream = System.in;
        System.setIn(in);
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        List<Card> cards = new ArrayList<>();
        cards.add(new Card("Черви", "Король"));
        cards.add(new Card("Черви", "Король"));
        cards.add(new Card("Черви", "Король"));
        cards.add(new Card("Черви", "Король"));
        BlackjackGame game = new BlackjackGame(cards);
        game.playRound();

        final String standardOutput = myOut.toString();
        assertTrue(standardOutput.contains("Ничья!"));
    }

    @Test
    public void test() {
        ByteArrayInputStream in = new ByteArrayInputStream("0\n".getBytes());
        InputStream inputStream = System.in;
        System.setIn(in);
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        List<Card> cards = new ArrayList<>();
        cards.add(new Card("Черви", "Король"));
        cards.add(new Card("Черви", "Туз"));
        cards.add(new Card("Пики", "Король"));
        cards.add(new Card("Крести", "Король"));
        BlackjackGame game = new BlackjackGame(cards);
        game.playRound();

        final String standardOutput = myOut.toString();
        assertTrue(standardOutput.contains("Вы выиграли раунд!"));
    }
}