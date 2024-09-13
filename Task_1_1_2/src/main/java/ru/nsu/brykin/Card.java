package ru.nsu.brykin;

/**
 * инициализирует структуру каждой карты.
 */
class Card {
    private String suit;
    private String rank;
    private int value;

    /**
     * собирает все параметры карты.
     */
    public Card(String suit, String rank) {
        final String[] ranks = {"Двойка", "Тройка", "Четвёрка", "Пятёрка",
                "Шестёрка", "Семёрка", "Восьмёрка", "Девятка",
                "Десятка", "Валет", "Дама", "Король", "Туз"};
        int value = 0;
        for (int i = 0; i < ranks.length; i++) {
            if (rank == ranks[i]) {
                if (i < 9) {
                    value = i + 2;
                } else if (i < 12) {
                    value = 10;
                } else {
                    value = 11;
                }
                this.suit = suit;
                this.rank = rank;
                this.value = value;
                break;
            }
        }
    }

    /**
     * возвращает значение карты.
     */
    public int getValue() {
        return value;
    }

    /**
     * возвращает достоинство и масть карты.
     */
    public String toString() {
        return rank + " " + suit;
    }
}
