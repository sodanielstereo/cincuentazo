package com.cincuentazo.model;

/**
 * Represents the card values used in the Cincuentazo game.
 */
public enum Value {

    /** Two. */
    TWO("2"),

    /** Three. */
    THREE("3"),

    /** Four. */
    FOUR("4"),

    /** Five. */
    FIVE("5"),

    /** Six. */
    SIX("6"),

    /** Seven. */
    SEVEN("7"),

    /** Eight. */
    EIGHT("8"),

    /** Nine. */
    NINE("9"),

    /** Ten. */
    TEN("10"),

    /** Jack. */
    JACK("J"),

    /** Queen. */
    QUEEN("Q"),

    /** King. */
    KING("K"),

    /** Ace. */
    ACE("A");

    private final String symbol;

    /**
     * Creates a card value with its visual symbol.
     *
     * @param symbol symbol representing the card value
     */
    Value(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Returns the visual symbol of the value.
     *
     * @return card symbol
     */
    public String getSymbol() {
        return symbol;
    }
}
