package com.cincuentazo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.cincuentazo.exceptions.InvalidMoveException;

/**
 * Unit tests for the {@link Table} class.
 */
class TableTest {

    /**
     * Verifies that the initial card defines the current sum and the visible card.
     */
    @Test
    void initialCardSetsCurrentSumAndTopCard() {
        Table table = new Table();
        Card initialCard = new Card(Value.KING, Suit.HEARTS);

        table.placeInitialCard(initialCard);

        assertEquals(initialCard, table.getTopCard());
        assertEquals(-10, table.getCurrentSum());
        assertEquals(1, table.getPlayedCards().size());
    }

    /**
     * Verifies that playing cards updates the sum and that overflows are rejected.
     */
    @Test
    void playingCardsUpdatesSumAndRejectsOverflow() {
        Table table = new Table();
        table.placeInitialCard(new Card(Value.TEN, Suit.CLUBS));

        Card secondCard = new Card(Value.TWO, Suit.SPADES);
        table.playCard(secondCard, 2);

        assertEquals(secondCard, table.getTopCard());
        assertEquals(12, table.getCurrentSum());
        assertEquals(2, table.getPlayedCards().size());

        assertThrows(InvalidMoveException.class,
                () -> table.playCard(new Card(Value.KING, Suit.DIAMONDS), 50));
    }

    /**
     * Verifies that recycling keeps only the top card and returns the removed cards.
     */
    @Test
    void removeCardsExceptTopKeepsOnlyVisibleCard() {
        Table table = new Table();
        Card initialCard = new Card(Value.FOUR, Suit.HEARTS);
        Card secondCard = new Card(Value.FIVE, Suit.CLUBS);
        Card topCard = new Card(Value.SIX, Suit.SPADES);

        table.placeInitialCard(initialCard);
        table.playCard(secondCard, 5);
        table.playCard(topCard, 6);

        List<Card> removedCards = table.removeCardsExceptTop();

        assertEquals(2, removedCards.size());
        assertTrue(removedCards.contains(initialCard));
        assertTrue(removedCards.contains(secondCard));
        assertEquals(1, table.getPlayedCards().size());
        assertEquals(topCard, table.getTopCard());
    }

    /**
     * Verifies that a new table starts empty.
     */
    @Test
    void newTableStartsEmpty() {
        Table table = new Table();

        assertNull(table.getTopCard());
        assertFalse(table.getPlayedCards().size() > 0);
        assertEquals(0, table.getCurrentSum());
    }
}
