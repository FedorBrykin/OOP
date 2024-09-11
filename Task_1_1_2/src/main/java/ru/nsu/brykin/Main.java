package ru.nsu.brykin;

import java.util.*;

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

/**
 * вся информация об игроке.
 */
class Player {
    private List<Card> hand;

    /**
     * рука игрока.
     */
    public Player() {
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

        for (Card card : hand) {
            score += card.getValue();
            if (card.getValue() == 11) aces++;
        }

        while (score > 21 && aces > 0) {
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

/**
 * сама игра.
 */
class BlackjackGame {
    private Deck deck;
    private Player player;
    private Player dealer;
    private int playerWins;
    private int dealerWins;

    /**
     * ход игры.
     */
    public BlackjackGame() {
        deck = new Deck();
        player = new Player();
        dealer = new Player();
        playerWins = 0;
        dealerWins = 0;
    }

    /**
     * раунд.
     */
    public void playRound() {
        player.clearHand();
        dealer.clearHand();

        System.out.println("    ");
        System.out.println("Раунд " + (playerWins + dealerWins + 1));

        // Раздача карт
        for (int i = 0; i < 2; i++) {
            player.addCard(deck.drawCard());
            dealer.addCard(deck.drawCard());
        }

        // Проверка на блэкджек
        if (checkBlackjack(player)) {
            System.out.println("Ваши карты: " + player.getHand() + " => " + player.getScore());
            System.out.println("Карты дилера: [" + dealer.getHand().getFirst() + ", <закрытая карта>]");
            System.out.println("Вы получили блэкджек! Вы выиграли раунд!");
            playerWins++;
            return;
        } else if (checkBlackjack(dealer)) {
            System.out.println("Ваши карты: " + player.getHand() + " => " + player.getScore());
            System.out.println("Карты дилера: [" + dealer.getHand().getFirst() + ", <закрытая карта>]");
            System.out.println("Дилер получил блэкджек! Вы проиграли раунд!");
            dealerWins++;
            return;
        }

        // Ход игрока
        while (true) {
            System.out.println("Ваши карты: " + player.getHand() + " => " + player.getScore());
            System.out.println("Карты дилера: [" + dealer.getHand().getFirst() + ", <закрытая карта>]");
            System.out.print("Введите “1”, чтобы взять карту, и “0”, чтобы остановиться: ");

            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            System.out.println("    ");
            if (choice == 1) {
                Card newCard = deck.drawCard();
                player.addCard(newCard);
                System.out.println("Вы открыли карту: " + newCard);
                if (player.getScore() > 21) {
                    System.out.println("Ваши карты: " + player.getHand() + " => " + player.getScore());
                    System.out.println("Вы превысили 21! Вы проиграли раунд!");
                    dealerWins++;
                    return;
                }
            } else {
                break;
            }
        }

        // Ход дилера
        System.out.println("Ход дилера");
        System.out.println("-------");
        System.out.println("Дилер открывает закрытую карту: " + dealer.getHand().get(1));
        System.out.println("Ваши карты: " + player.getHand() + " => " + player.getScore());
        System.out.println("Карты дилера: " + dealer.getHand() + " => " + dealer.getScore());
        while (dealer.getScore() < 17) {
            System.out.println("    ");
            Card newDealerCard = deck.drawCard();
            dealer.addCard(newDealerCard);
            System.out.println("Дилер открывает карту: " + newDealerCard);
            System.out.println("Ваши карты: " + player.getHand() + " => " + player.getScore());
            System.out.println("Карты дилера: " + dealer.getHand() + " => " + dealer.getScore());
        }

        // Итоги раунда
        System.out.println("    ");
        if (dealer.getScore() > 21 || player.getScore() > dealer.getScore()) {
            System.out.println("Вы выиграли раунд!");
            playerWins++;
        } else if (player.getScore() < dealer.getScore()) {
            System.out.println("Вы проиграли раунд!");
            dealerWins++;
        } else {
            System.out.println("Ничья!");
        }

        System.out.println("Счет: Вы - " + playerWins + ", Дилер - " + dealerWins);
    }

    /**
     * проверка на победу сразу.
     */
    boolean checkBlackjack(Player player) {
        return player.getScore() == 21 && player.getHand().size() == 2;
    }

    /**
     * начало игры.
     */
    public static void main(String[] args) {
        BlackjackGame game = new BlackjackGame();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            game.playRound();
            System.out.print("Хотите сыграть еще раз? (1 - да, 0 - нет): ");
            int choice = scanner.nextInt();
            if (choice == 0) break;
        }

        System.out.println("Спасибо за игру!");
    }
}