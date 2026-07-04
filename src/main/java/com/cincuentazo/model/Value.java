package com.cincuentazo.model;

/**
 * Representa los valores de una carta de poker dentro del juego Cincuentazo.
 */
public enum Value {
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    TEN("10"),
    JACK("J"),
    QUEEN("Q"),
    KING("K"),
    ACE("A");

    private final String symbol;

    /**
     * Crea un valor de carta con su símbolo visual.
     *
     * @param symbol símbolo que representa el valor de la carta
     */
    Value(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Retorna el símbolo visual del valor.
     *
     * @return símbolo de la carta
     */
    public String getSymbol() {
        return symbol;
    }
}