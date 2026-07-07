package com.cincuentazo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.cincuentazo.exceptions.EmptyDeckException;

/**
 * Unit tests for the {@link Deck} class.
 */
class DeckTest {

    /**
     * Verifies that a new deck starts with 52 cards.
     */
    @Test
    void newDeckStartsWithFiftyTwoCards() {
        Deck deck = new Deck();

        assertEquals(52, deck.size());
        assertFalse(deck.isEmpty());
    }

    /**
     * Verifies that drawing a card removes one card from the deck.
     */
    @Test
    void drawRemovesOneCardFromDeck() throws EmptyDeckException {
        Deck deck = new Deck();

        Card drawnCard = deck.draw();

        assertNotNull(drawnCard);
        assertEquals(51, deck.size());
    }

    /**
     * Verifies that drawing all cards leaves the deck empty.
     */
    @Test
    void drawingAllCardsLeavesDeckEmpty() throws EmptyDeckException {
        Deck deck = new Deck();

        for (int i = 0; i < 52; i++) {
            deck.draw();
        }

        assertTrue(deck.isEmpty());
        assertEquals(0, deck.size());
    }

    /**
     * Verifies that drawing from an empty deck throws an exception.
     */
    @Test
    void drawingFromEmptyDeckThrowsException() throws EmptyDeckException {
        Deck deck = new Deck();

        for (int i = 0; i < 52; i++) {
            deck.draw();
        }

        assertThrows(EmptyDeckException.class, deck::draw);
    }
}
