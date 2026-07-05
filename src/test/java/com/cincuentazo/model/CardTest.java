package com.cincuentazo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Pruebas unitarias para la clase Card.
 */
class CardTest {

    @Test
    void numberCardBetweenTwoAndEightUsesItsOwnValue() {
        Card twoOfHearts = new Card(Value.TWO, Suit.HEARTS);

        assertTrue(twoOfHearts.canBePlayed(48));
        assertFalse(twoOfHearts.canBePlayed(49));
        assertEquals(2, twoOfHearts.getBestPlayableValue(0));
    }

    @Test
    void nineCardHasZeroValue() {
        Card nineOfSpades = new Card(Value.NINE, Suit.SPADES);

        assertTrue(nineOfSpades.canBePlayed(50));
        assertEquals(0, nineOfSpades.getBestPlayableValue(50));
    }

    @Test
    void tenCardAddsTen() {
        Card tenOfClubs = new Card(Value.TEN, Suit.CLUBS);

        assertTrue(tenOfClubs.canBePlayed(40));
        assertFalse(tenOfClubs.canBePlayed(41));
        assertEquals(10, tenOfClubs.getBestPlayableValue(20));
    }

    @Test
    void faceCardsSubtractTen() {
        Card jackOfDiamonds = new Card(Value.JACK, Suit.DIAMONDS);
        Card queenOfHearts = new Card(Value.QUEEN, Suit.HEARTS);
        Card kingOfClubs = new Card(Value.KING, Suit.CLUBS);

        assertTrue(jackOfDiamonds.canBePlayed(50));
        assertTrue(queenOfHearts.canBePlayed(50));
        assertTrue(kingOfClubs.canBePlayed(50));

        assertEquals(-10, jackOfDiamonds.getBestPlayableValue(50));
        assertEquals(-10, queenOfHearts.getBestPlayableValue(30));
        assertEquals(-10, kingOfClubs.getBestPlayableValue(10));
    }

    @Test
    void aceUsesTenWhenPossibleAndOneWhenNecessary() {
        Card aceOfSpades = new Card(Value.ACE, Suit.SPADES);

        assertTrue(aceOfSpades.canBePlayed(40));
        assertEquals(10, aceOfSpades.getBestPlayableValue(40));

        assertTrue(aceOfSpades.canBePlayed(45));
        assertEquals(1, aceOfSpades.getBestPlayableValue(45));

        assertFalse(aceOfSpades.canBePlayed(50));
    }
}