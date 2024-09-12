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
    public Card(String suit, String rank, int i) {
        int value = 0;
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
