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
    public Card(String suit, String rank, int value) {
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
