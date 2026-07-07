package com.cincuentazo.model;

/**
 * Represents a card play with the card played and the value applied to the table.
 */
public class CardPlay {

    private final Card card;
    private final int usedValue;

    /**
     * Creates a card play.
     *
     * @param card played card
     * @param usedValue value used to modify the table sum
     */
    public CardPlay(Card card, int usedValue) {
        this.card = card;
        this.usedValue = usedValue;
    }

    /**
     * Returns the played card.
     *
     * @return played card
     */
    public Card getCard() {
        return card;
    }

    /**
     * Returns the value used in the play.
     *
     * @return used value
     */
    public int getUsedValue() {
        return usedValue;
    }
}
