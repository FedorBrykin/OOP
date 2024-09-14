package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayersHandTest {

    private PlayersHand playersHand;
    private Card aceCard;
    private Card tenCard;
    private Card kingCard;

    @BeforeEach
    void setUp() {
        playersHand = new PlayersHand();
        aceCard = new Card("Черви", "Туз"); // Предположим, что туз имеет значение 11
        tenCard = new Card("Черви", "Десятка"); // Десятка имеет значение 10
        kingCard = new Card("Черви", "Король"); // Король также имеет значение 10
    }

    @Test
    void testAddCard() {
        playersHand.addCard(aceCard);
        playersHand.addCard(tenCard);
        assertEquals(2, playersHand.getSize());
    }

    @Test
    void testGetScoreWithoutAces() {
        playersHand.addCard(tenCard);
        playersHand.addCard(kingCard);
        assertEquals(20, playersHand.getScore());
    }

    @Test
    void testGetScoreWithAces() {
        playersHand.addCard(aceCard);
        playersHand.addCard(tenCard);
        assertEquals(21, playersHand.getScore());

        playersHand.addCard(aceCard); // Добавляем еще один туз
        assertEquals(12, playersHand.getScore()); // 21 - 10 (один туз превращается из 11 в 1)
    }

    @Test
    void testGetScoreExceedsMax() {
        playersHand.addCard(aceCard);
        playersHand.addCard(aceCard);
        playersHand.addCard(kingCard); // 11 + 11 + 10 = 32
        assertEquals(12, playersHand.getScore()); // 32 - 20 (два туза превращаются из 11 в 1)
    }

    @Test
    void testCardsRow() {
        playersHand.addCard(aceCard);
        playersHand.addCard(tenCard);
        StringBuilder expected = new StringBuilder();
        expected.append(aceCard.toString()).append(", ");
        expected.append(tenCard.toString()).append(", ");
        assertEquals(expected.toString(), playersHand.cardsRow().toString());
    }

    @Test
    void testGetSize() {
        assertEquals(0, playersHand.getSize());
        playersHand.addCard(aceCard);
        assertEquals(1, playersHand.getSize());
    }

    @Test
    void testGetCard() {
        playersHand.addCard(aceCard);
        playersHand.addCard(tenCard);
        assertEquals(aceCard, playersHand.getCard(1));
        assertEquals(tenCard, playersHand.getCard(2));
    }

    @Test
    void testClearHand() {
        playersHand.addCard(aceCard);
        playersHand.addCard(tenCard);
        assertEquals(2, playersHand.getSize());
        playersHand.clearHand();
        assertEquals(0, playersHand.getSize());
    }
}
