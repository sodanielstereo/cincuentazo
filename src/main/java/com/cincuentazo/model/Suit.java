package com.cincuentazo.model;

/**
 * Represents the suits of a standard poker deck.
 */
public enum Suit {

    /** Hearts suit. */
    HEARTS("♥"),

    /** Diamonds suit. */
    DIAMONDS("♦"),

    /** Clubs suit. */
    CLUBS("♣"),

    /** Spades suit. */
    SPADES("♠");

    private final String symbol;

    /**
     * Creates a suit with its visual symbol.
     *
     * @param symbol suit symbol
     */
    Suit(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Returns the visual symbol of the suit.
     *
     * @return suit symbol
     */
    public String getSymbol() {
        return symbol;
    }
}
