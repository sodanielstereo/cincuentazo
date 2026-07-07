package com.cincuentazo.model;

import java.util.ArrayList;
import java.util.List;

import com.cincuentazo.exceptions.InvalidMoveException;

/**
 * Represents the game table, including played cards and the current sum.
 */
public class Table {

    private final List<Card> playedCards;
    private int currentSum;

    /**
     * Creates an empty table.
     */
    public Table() {
        this.playedCards = new ArrayList<>();
        this.currentSum = 0;
    }

    /**
     * Places the initial card on the table and sets the initial sum.
     *
     * @param card initial card
     */
    public void placeInitialCard(Card card) {
        playedCards.clear();
        playedCards.add(card);
        currentSum = card.getInitialValue();
    }

    /**
     * Plays a card on the table and updates the sum.
     *
     * @param card played card
     * @param usedValue value applied from the card
     * @throws InvalidMoveException if the play would exceed the maximum sum of 50
     */
    public void playCard(Card card, int usedValue) {
        if (currentSum + usedValue > 50) {
            throw new InvalidMoveException("La jugada supera la suma máxima de 50.");
        }

        playedCards.add(card);
        currentSum += usedValue;
    }

    /**
     * Returns the visible card on the table.
     *
     * @return top card played, or {@code null} if the table is empty
     */
    public Card getTopCard() {
        if (playedCards.isEmpty()) {
            return null;
        }

        return playedCards.get(playedCards.size() - 1);
    }

    /**
     * Removes all cards from the table except the top card.
     *
     * @return removed cards to be recycled
     */
    public List<Card> removeCardsExceptTop() {
        List<Card> removedCards = new ArrayList<>();

        while (playedCards.size() > 1) {
            removedCards.add(playedCards.remove(0));
        }

        return removedCards;
    }

    /**
     * Returns the current table sum.
     *
     * @return current sum
     */
    public int getCurrentSum() {
        return currentSum;
    }

    /**
     * Returns a copy of the cards played on the table.
     *
     * @return played cards
     */
    public List<Card> getPlayedCards() {
        return new ArrayList<>(playedCards);
    }
}
