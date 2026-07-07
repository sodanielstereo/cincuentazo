package com.cincuentazo.model;

import java.util.List;

/**
 * Represents a playing card used in the Cincuentazo game.
 */
public class Card {

    private final Value value;
    private final Suit suit;

    /**
     * Creates a card with the given value and suit.
     *
     * @param value card value
     * @param suit card suit
     */
    public Card(Value value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }

    /**
     * Returns the possible numeric values of the card according to game rules.
     * The ace may be worth 1 or 10.
     *
     * @return list of possible card values
     */
    public List<Integer> getPossibleValues() {
        return switch (value) {
            case TWO -> List.of(2);
            case THREE -> List.of(3);
            case FOUR -> List.of(4);
            case FIVE -> List.of(5);
            case SIX -> List.of(6);
            case SEVEN -> List.of(7);
            case EIGHT -> List.of(8);
            case NINE -> List.of(0);
            case TEN -> List.of(10);
            case JACK, QUEEN, KING -> List.of(-10);
            case ACE -> List.of(1, 10);
        };
    }

    /**
     * Returns the value used when the card initializes the table sum.
     * The ace starts with a value of 1.
     *
     * @return initial card value
     */
    public int getInitialValue() {
        if (value == Value.ACE) {
            return 1;
        }

        return getPossibleValues().get(0);
    }

    /**
     * Checks whether the card can be played without exceeding 50.
     *
     * @param currentSum current table sum
     * @return {@code true} if the card can be played, {@code false} otherwise
     */
    public boolean canBePlayed(int currentSum) {
        return getPossibleValues()
                .stream()
                .anyMatch(cardValue -> currentSum + cardValue <= 50);
    }

    /**
     * Returns the best playable value for the card.
     * For the ace, chooses 10 if it does not exceed 50; otherwise chooses 1.
     *
     * @param currentSum current table sum
     * @return best possible card value
     * @throws IllegalStateException if the card cannot be played without exceeding 50
     */
    public int getBestPlayableValue(int currentSum) {
        return getPossibleValues()
                .stream()
                .filter(cardValue -> currentSum + cardValue <= 50)
                .max(Integer::compareTo)
                .orElseThrow(() -> new IllegalStateException("La carta no puede ser jugada sin superar 50."));
    }

    /**
     * Returns the card value.
     *
     * @return card value
     */
    public Value getValue() {
        return value;
    }

    /**
     * Returns the card suit.
     *
     * @return card suit
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Returns the visual representation of the card.
     *
     * @return card text
     */
    @Override
    public String toString() {
        return value.getSymbol() + suit.getSymbol();
    }
}
