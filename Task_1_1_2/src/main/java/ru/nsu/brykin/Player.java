package ru.nsu.brykin;

import java.util.ArrayList;
import java.util.List;

/**
 * вся информация об игроке.
 */
class PlayersHand {
    private List<Card> hand;

    /**
     * рука игрока.
     */
    public PlayersHand() {
        hand = new ArrayList<>();
    }

    /**
     * добавляет карту в руку.
     */
    public void addCard(Card card) {
        hand.add(card);
    }

    /**
     * подсчёт очков.
     */
    public int getScore() {
        int score = 0;
        int aces = 0;
        int maxScore = 21;

        for (Card card : hand) {
            score += card.getValue();
            if (card.getValue() == 11) aces++;
        }

        while (score > maxScore && aces > 0) {
            score -= 10; // Превращаем туз из 11 в 1
            aces--;
        }

        return score;
    }

    /**
     * выдаёт содержимое руки по запросу.
     */
    public List<Card> getHand() {
        return hand;
    }

    /**
     * очищает руку.
     */
    public void clearHand() {
        hand.clear();
    }
}