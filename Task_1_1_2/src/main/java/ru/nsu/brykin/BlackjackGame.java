package ru.nsu.brykin;

/**
 * сама игра.
 */
public class BlackjackGame {
    private Deck deck;
    private PlayersHand player;
    private PlayersHand dealer;
    private int playerWins;
    private int dealerWins;
    static final int maxScore = 21;

    /**
     * ход игры.
     */
    public BlackjackGame() {
        deck = new Deck();
        player = new PlayersHand();
        dealer = new PlayersHand();
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
        cardsDealing(player, dealer);

        // Проверка на блэкджек
        if (checkBlackjack(player)) {
            outputTextClosedCard();
            System.out.println("Вы получили блэкджек! Вы выиграли раунд!");
            playerWins++;
            return;
        } else if (checkBlackjack(dealer)) {
            outputTextClosedCard();
            System.out.println("Дилер получил блэкджек! Вы проиграли раунд!");
            dealerWins++;
            return;
        }

        outputTextClosedCard();

        // Ход игрока
        playersTurn(player, dealer);
        if (player.getScore() > maxScore) {
            System.out.println("    ");
            System.out.println("Вы проиграли раунд!");
            dealerWins++;
            return;
        }

        // Ход дилера
        dealersTurn(player, dealer);

        // Итоги раунда
        roundResults(player, dealer);

        System.out.println("Счет: Вы - " + playerWins + ", Дилер - " + dealerWins);
    }

    /**
     * проверка на победу сразу.
     */
    boolean checkBlackjack(PlayersHand player) {
        return player.getScore() == maxScore && player.getSize() == 2;
    }

    /**
     * начало игры.
     */
    public static void main(String[] args) {
        BlackjackGame game = new BlackjackGame();

        game.playRound();

    }

    /**
     * раздача.
     */
    private void cardsDealing(PlayersHand player, PlayersHand dealer) {
        for (int i = 0; i < 2; i++) {
            player.addCard(deck.drawCard());
            dealer.addCard(deck.drawCard());
        }
    }

    /**
     * ход игрока.
     */
    private void playersTurn(PlayersHand player, PlayersHand dealer) {
        while (player.getScore() < 17) {
            System.out.println("    ");
            Card newPlayerCard = deck.drawCard();
            player.addCard(newPlayerCard);
            System.out.println("Вы открыли карту: " + newPlayerCard);
            outputTextClosedCard();
        }
    }

    /**
     * ход дилера.
     */
    private void dealersTurn(PlayersHand player, PlayersHand dealer) {
        System.out.println("Ход дилера");
        System.out.println("-------");
        System.out.println("Дилер открывает закрытую карту: " + dealer.getCard(2));
        deckStatus();

        while (dealer.getScore() < 17) {
            System.out.println("    ");
            Card newDealerCard = deck.drawCard();
            dealer.addCard(newDealerCard);
            System.out.println("Дилер открывает карту: " + newDealerCard);
            deckStatus();
        }
    }

    /**
     * итоги раунда.
     */
    private void roundResults(PlayersHand player, PlayersHand dealer) {
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
    }

    /**
     * output text when the card is closed.
     */
    private void outputTextClosedCard() {
        System.out.println("Ваши карты: " + player.cardsRow() + " => " + player.getScore());
        System.out.println("Карты дилера: [" + dealer.getCard(1) +
                ", <закрытая карта>]");
    }

    private void deckStatus() {
        System.out.println("Ваши карты: " + player.cardsRow() + " => " + player.getScore());
        System.out.println("Карты дилера: " + dealer.cardsRow() + " => " + dealer.getScore());
    }
}