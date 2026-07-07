package com.cincuentazo.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import com.cincuentazo.exceptions.EmptyDeckException;

/**
 * Represents the deck of cards used in the Cincuentazo game.
 */
public class Deck {

    private final Deque<Card> cards;

    /**
     * Creates a full 52-card deck and shuffles it.
     */
    public Deck() {
        this.cards = new ArrayDeque<>();
        createFullDeck();
        shuffle();
    }

    /**
     * Creates all 52 cards of a standard poker deck.
     */
    private void createFullDeck() {
        for (Suit suit : Suit.values()) {
            for (Value value : Value.values()) {
                cards.addLast(new Card(value, suit));
            }
        }
    }

    /**
     * Shuffles the current cards in the deck.
     */
    private void shuffle() {
        List<Card> shuffledCards = new ArrayList<>(cards);
        Collections.shuffle(shuffledCards);

        cards.clear();
        cards.addAll(shuffledCards);
    }

    /**
     * Draws a card from the top of the deck.
     *
     * @return drawn card
     * @throws EmptyDeckException if the deck is empty
     */
    public Card draw() throws EmptyDeckException {
        if (cards.isEmpty()) {
            throw new EmptyDeckException("No hay cartas disponibles en el mazo.");
        }

        return cards.removeFirst();
    }

    /**
     * Sends cards to the bottom of the deck.
     *
     * @param returnedCards cards to place at the bottom
     */
    public void sendToBottom(Collection<Card> returnedCards) {
        cards.addAll(returnedCards);
    }

    /**
     * Shuffles a group of cards and sends them to the bottom of the deck.
     *
     * @param returnedCards recycled cards
     */
    public void shuffleAndSendToBottom(Collection<Card> returnedCards) {
        List<Card> shuffledCards = new ArrayList<>(returnedCards);
        Collections.shuffle(shuffledCards);
        cards.addAll(shuffledCards);
    }

    /**
     * Checks whether the deck is empty.
     *
     * @return {@code true} if there are no cards, {@code false} otherwise
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    /**
     * Returns the number of cards available in the deck.
     *
     * @return number of cards
     */
    public int size() {
        return cards.size();
    }
}
