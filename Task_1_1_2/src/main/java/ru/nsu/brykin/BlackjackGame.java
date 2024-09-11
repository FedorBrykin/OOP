package ru.nsu.brykin;


import java.util.Scanner;

/**
 * сама игра.
 */
public class BlackjackGame {
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

        System.out.println("Ваши карты: " + player.getHand() + " => " + player.getScore());
        System.out.println("Карты дилера: [" + dealer.getHand().getFirst() + ", <закрытая карта>]");

        /* Ход игрока
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
        }*/

        // Ход игрока для гита
        while (player.getScore() < 17) {
            System.out.println("    ");
            Card newPlayerCard = deck.drawCard();
            player.addCard(newPlayerCard);
            System.out.println("Вы открыли карту: " + newPlayerCard);
            System.out.println("Ваши карты: " + player.getHand() + " => " + player.getScore());
            System.out.println("Карты дилера: " + dealer.getHand() + " => " + dealer.getScore());
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

       /* Scanner scanner = new Scanner(System.in);

        while (true) {
            game.playRound();
            System.out.print("Хотите сыграть еще раз? (1 - да, 0 - нет): ");
            int choice = scanner.nextInt();
            if (choice == 0) break;
        }

        System.out.println("Спасибо за игру!");*/

        game.playRound();

    }

    public int getPlayerWins() {
        return playerWins;
    }

    public int getDealerWins() {
        return dealerWins;
    }
}