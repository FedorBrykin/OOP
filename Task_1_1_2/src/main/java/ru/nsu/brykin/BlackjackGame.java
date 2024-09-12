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
        cardsDealing();

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

        // Ход игрока
        PlayersTurn(player, dealer);
        if (player.getScore() > 21) {
            System.out.println("    ");
            System.out.println("Вы проиграли раунд!");
            dealerWins++;
            return;
        }

        // Ход дилера
        DealersTurn(player, dealer);

        // Итоги раунда
        RoundResults(player, dealer);

        System.out.println("Счет: Вы - " + playerWins + ", Дилер - " + dealerWins);
    }

    /**
     * проверка на победу сразу.
     */
    boolean checkBlackjack(PlayersHand player) {
        return player.getScore() == 21 && player.getHand().size() == 2;
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
    private void cardsDealing() {
        for (int i = 0; i < 2; i++) {
            player.addCard(deck.drawCard());
            dealer.addCard(deck.drawCard());
        }
    }

    /**
     * ход игрока.
     */
    private void PlayersTurn(PlayersHand player, PlayersHand dealer) {
        while (player.getScore() < 17) {
            System.out.println("    ");
            Card newPlayerCard = deck.drawCard();
            player.addCard(newPlayerCard);
            System.out.println("Вы открыли карту: " + newPlayerCard);
            System.out.println("Ваши карты: " + player.getHand() + " => " + player.getScore());
            System.out.println("Карты дилера: [" + dealer.getHand().getFirst() + ", <закрытая карта>]");
        }
    }

    /**
     * ход дилера.
     */
    private void DealersTurn(PlayersHand player, PlayersHand dealer) {
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
    }

    /**
     * итоги раунда.
     */
    private void RoundResults(PlayersHand player, PlayersHand dealer) {
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
}