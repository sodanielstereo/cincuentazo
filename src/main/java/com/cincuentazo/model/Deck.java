package com.cincuentazo.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

/**
 * Representa el mazo de cartas del juego Cincuentazo.
 */
public class Deck {

    private final Deque<Card> cards;

    /**
     * Crea un mazo completo de 52 cartas y lo baraja.
     */
    public Deck() {
        this.cards = new ArrayDeque<>();
        createFullDeck();
        shuffle();
    }

    /**
     * Crea las 52 cartas de la baraja de poker.
     */
    private void createFullDeck() {
        for (Suit suit : Suit.values()) {
            for (Value value : Value.values()) {
                cards.addLast(new Card(value, suit));
            }
        }
    }

    /**
     * Baraja las cartas actuales del mazo.
     */
    public void shuffle() {
        List<Card> shuffledCards = new ArrayList<>(cards);
        Collections.shuffle(shuffledCards);

        cards.clear();
        cards.addAll(shuffledCards);
    }

    /**
     * Toma una carta de la parte superior del mazo.
     *
     * @return carta tomada
     */
    public Card draw() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("No hay cartas disponibles en el mazo.");
        }

        return cards.removeFirst();
    }

    /**
     * Envía cartas al final del mazo.
     *
     * @param returnedCards cartas que se enviarán al final
     */
    public void sendToBottom(Collection<Card> returnedCards) {
        cards.addAll(returnedCards);
    }

    /**
     * Baraja un grupo de cartas y las envía al final del mazo.
     *
     * @param returnedCards cartas recicladas
     */
    public void shuffleAndSendToBottom(Collection<Card> returnedCards) {
        List<Card> shuffledCards = new ArrayList<>(returnedCards);
        Collections.shuffle(shuffledCards);
        cards.addAll(shuffledCards);
    }

    /**
     * Verifica si el mazo está vacío.
     *
     * @return true si no hay cartas, false en caso contrario
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    /**
     * Retorna la cantidad de cartas disponibles.
     *
     * @return tamaño del mazo
     */
    public int size() {
        return cards.size();
    }
}