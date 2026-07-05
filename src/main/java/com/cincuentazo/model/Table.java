package com.cincuentazo.model;

import java.util.ArrayList;
import java.util.List;

import com.cincuentazo.exceptions.InvalidMoveException;

/**
 * Representa la mesa del juego, incluyendo las cartas jugadas y la suma actual.
 */
public class Table {

    private final List<Card> playedCards;
    private int currentSum;

    /**
     * Crea una mesa vacía.
     */
    public Table() {
        this.playedCards = new ArrayList<>();
        this.currentSum = 0;
    }

    /**
     * Coloca la carta inicial en la mesa y define la suma inicial.
     *
     * @param card carta inicial
     */
    public void placeInitialCard(Card card) {
        playedCards.clear();
        playedCards.add(card);
        currentSum = card.getInitialValue();
    }

    /**
     * Juega una carta sobre la mesa y actualiza la suma.
     *
     * @param card carta jugada
     * @param usedValue valor usado de la carta
     */
    public void playCard(Card card, int usedValue) {
        if (currentSum + usedValue > 50) {
            throw new InvalidMoveException("La jugada supera la suma máxima de 50.");
        }

        playedCards.add(card);
        currentSum += usedValue;
    }

    /**
     * Retorna la carta visible en la mesa.
     *
     * @return última carta jugada
     */
    public Card getTopCard() {
        if (playedCards.isEmpty()) {
            return null;
        }

        return playedCards.get(playedCards.size() - 1);
    }

    /**
     * Retira todas las cartas de la mesa excepto la última.
     *
     * @return cartas retiradas para reciclar
     */
    public List<Card> removeCardsExceptTop() {
        List<Card> removedCards = new ArrayList<>();

        while (playedCards.size() > 1) {
            removedCards.add(playedCards.remove(0));
        }

        return removedCards;
    }

    public int getCurrentSum() {
        return currentSum;
    }

    public List<Card> getPlayedCards() {
        return new ArrayList<>(playedCards);
    }
}