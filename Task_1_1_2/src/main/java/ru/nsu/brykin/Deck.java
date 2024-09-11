package ru.nsu.brykin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * создаёт и перемешивает колоду.
 */
class Deck {
    List<Card> cards;

    /**
     * создание колоды карт.
     */
    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"Пик", "Червей", "Буби", "Крести"};
        String[] ranks = {"Двойка", "Тройка", "Четвёрка", "Пятёрка", "Шестёрка", "Семёрка", "Восьмёрка", "Девятка",
                "Десятка", "Валет", "Дама", "Король", "Туз"};

        for (String suit : suits) {
            for (int i = 0; i < ranks.length; i++) {
                int value = 0;
                if (i <= 9) {
                    value = i + 2;
                } else {
                    value = 10;
                }
                if (ranks[i].equals("Туз")) {
                    value = 11;
                }
                cards.add(new Card(suit, ranks[i], value));
            }
        }
        Collections.shuffle(cards);
    }

    /**
     * выдаёт карту.
     */
    public Card drawCard() {
        return cards.remove(cards.size() - 1);
    }
}
