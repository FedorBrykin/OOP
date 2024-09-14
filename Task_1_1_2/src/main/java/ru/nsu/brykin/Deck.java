package ru.nsu.brykin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * создаёт и перемешивает колоду.
 */
class Deck {
    private List<Card> cards;

    /**
     * создание колоды карт.
     */
    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"Пик", "Червей", "Буби", "Крести"};
        String[] ranks = {"Двойка", "Тройка", "Четвёрка", "Пятёрка",
                          "Шестёрка", "Семёрка", "Восьмёрка", "Девятка",
                          "Десятка", "Валет", "Дама", "Король", "Туз"};

        for (String suit : suits) {
            for (int i = 0; i < ranks.length; i++) {
                cards.add(new Card(suit, ranks[i]));
            }
        }
        Collections.shuffle(cards);
    }

    public Deck(List<Card> cards) {
        this.cards = cards;
    }

    /**
     * выдаёт карту.
     */
    public Card drawCard() {
        return cards.remove(cards.size() - 1);
    }


    /**
     * возвращает номер.
     */
    public int numbers() {
        return cards.size();
    }
}
