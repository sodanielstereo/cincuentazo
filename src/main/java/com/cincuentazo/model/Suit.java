package com.cincuentazo.model;

/**
 * Representa los palos de una baraja de poker.
 */
public enum Suit {
    HEARTS("♥"),
    DIAMONDS("♦"),
    CLUBS("♣"),
    SPADES("♠");

    private final String symbol;

    /**
     * Crea un palo con su símbolo visual.
     *
     * @param symbol símbolo del palo
     */
    Suit(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Retorna el símbolo visual del palo.
     *
     * @return símbolo del palo
     */
    public String getSymbol() {
        return symbol;
    }
}