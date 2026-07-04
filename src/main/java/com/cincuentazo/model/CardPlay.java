package com.cincuentazo.model;

/**
 * Representa una jugada realizada con una carta y el valor usado en la mesa.
 */
public class CardPlay {

    private final Card card;
    private final int usedValue;

    /**
     * Crea una jugada de carta.
     *
     * @param card carta jugada
     * @param usedValue valor usado para modificar la suma de la mesa
     */
    public CardPlay(Card card, int usedValue) {
        this.card = card;
        this.usedValue = usedValue;
    }

    /**
     * Retorna la carta jugada.
     *
     * @return carta jugada
     */
    public Card getCard() {
        return card;
    }

    /**
     * Retorna el valor usado en la jugada.
     *
     * @return valor usado
     */
    public int getUsedValue() {
        return usedValue;
    }
}