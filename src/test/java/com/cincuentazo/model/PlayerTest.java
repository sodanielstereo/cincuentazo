package com.cincuentazo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.cincuentazo.exceptions.InvalidMoveException;

/**
 * Unit tests for the {@link Player} hierarchy.
 */
class PlayerTest {

    /**
     * Verifies that a player can store at most four cards and rejects the fifth one.
     */
    @Test
    void playerRejectsMoreThanFourCards() {
        Player player = new RealPlayer("Daniel");

        player.addCard(new Card(Value.TWO, Suit.HEARTS));
        player.addCard(new Card(Value.THREE, Suit.CLUBS));
        player.addCard(new Card(Value.FOUR, Suit.SPADES));
        player.addCard(new Card(Value.FIVE, Suit.DIAMONDS));

        assertThrows(InvalidMoveException.class,
                () -> player.addCard(new Card(Value.SIX, Suit.HEARTS)));
    }

    /**
     * Verifies that removing cards by index and by collection state works as expected.
     */
    @Test
    void playerCanRemoveCardsAndReturnsIndependentHandCopies() {
        Player player = new RealPlayer("Daniel");
        Card firstCard = new Card(Value.TWO, Suit.HEARTS);
        Card secondCard = new Card(Value.THREE, Suit.CLUBS);

        player.addCard(firstCard);
        player.addCard(secondCard);

        assertTrue(player.hasPlayableCard(48));
        assertFalse(player.hasPlayableCard(49));

        assertEquals(firstCard, player.removeCardAt(0));
        assertEquals(1, player.getHand().size());

        List<Card> handCopy = player.getHand();
        handCopy.clear();

        assertEquals(1, player.getHand().size());
        assertEquals(1, player.removeAllCards().size());
        assertTrue(player.getHand().isEmpty());
    }

    /**
     * Verifies that the active flag can be toggled.
     */
    @Test
    void playerActiveFlagCanBeToggled() {
        Player player = new RealPlayer("Daniel");

        assertTrue(player.isActive());

        player.setActive(false);

        assertFalse(player.isActive());
    }
}
