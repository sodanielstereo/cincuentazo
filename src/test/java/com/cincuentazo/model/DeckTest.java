package com.cincuentazo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.cincuentazo.exceptions.EmptyDeckException;

/**
 * Pruebas unitarias para la clase Deck.
 */
class DeckTest {

    @Test
    void newDeckStartsWithFiftyTwoCards() {
        Deck deck = new Deck();

        assertEquals(52, deck.size());
        assertFalse(deck.isEmpty());
    }

    @Test
    void drawRemovesOneCardFromDeck() throws EmptyDeckException {
        Deck deck = new Deck();

        Card drawnCard = deck.draw();

        assertNotNull(drawnCard);
        assertEquals(51, deck.size());
    }

    @Test
    void drawingAllCardsLeavesDeckEmpty() throws EmptyDeckException {
        Deck deck = new Deck();

        for (int i = 0; i < 52; i++) {
            deck.draw();
        }

        assertTrue(deck.isEmpty());
        assertEquals(0, deck.size());
    }

    @Test
    void drawingFromEmptyDeckThrowsException() throws EmptyDeckException {
        Deck deck = new Deck();

        for (int i = 0; i < 52; i++) {
            deck.draw();
        }

        assertThrows(EmptyDeckException.class, deck::draw);
    }
}