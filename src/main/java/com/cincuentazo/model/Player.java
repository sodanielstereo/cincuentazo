package com.cincuentazo.model;

import java.util.ArrayList;
import java.util.List;

import com.cincuentazo.exceptions.InvalidMoveException;

/**
 * Represents a player in the Cincuentazo game.
 */
public abstract class Player {

    /** Maximum number of cards allowed in a player's hand. */
    public static final int MAX_HAND_SIZE = 4;

    private final String name;
    private final boolean artificial;
    private final List<Card> hand;
    private boolean active;

    /**
     * Creates a player.
     *
     * @param name player name
     * @param artificial whether the player is artificial
     */
    protected Player(String name, boolean artificial) {
        this.name = name;
        this.artificial = artificial;
        this.hand = new ArrayList<>();
        this.active = true;
    }

    /**
     * Adds a card to the player's hand.
     *
     * @param card card to add
     * @throws InvalidMoveException if the hand already has the maximum number of cards
     */
    public void addCard(Card card) {
        if (hand.size() >= MAX_HAND_SIZE) {
            throw new InvalidMoveException("El jugador ya tiene 4 cartas en la mano.");
        }

        hand.add(card);
    }

    /**
     * Removes a card from the hand by index.
     *
     * @param index card position
     * @return removed card
     * @throws InvalidMoveException if the index is invalid
     */
    public Card removeCardAt(int index) {
        if (index < 0 || index >= hand.size()) {
            throw new InvalidMoveException("La posición de la carta no es válida.");
        }

        return hand.remove(index);
    }

    /**
     * Removes a specific card from the hand.
     *
     * @param card card to remove
     * @return {@code true} if the card was removed
     */
    public boolean removeCard(Card card) {
        return hand.remove(card);
    }

    /**
     * Checks whether the player has at least one playable card.
     *
     * @param currentSum current table sum
     * @return {@code true} if the player has a playable card
     */
    public boolean hasPlayableCard(int currentSum) {
        return hand.stream().anyMatch(card -> card.canBePlayed(currentSum));
    }

    /**
     * Removes all cards from the hand.
     *
     * @return cards that were in the hand
     */
    public List<Card> removeAllCards() {
        List<Card> removedCards = new ArrayList<>(hand);
        hand.clear();
        return removedCards;
    }

    /**
     * Returns a copy of the player's hand.
     *
     * @return player hand
     */
    public List<Card> getHand() {
        return new ArrayList<>(hand);
    }

    /**
     * Returns the player's name.
     *
     * @return player name
     */
    public String getName() {
        return name;
    }

    /**
     * Indicates whether the player is artificial.
     *
     * @return {@code true} if the player is artificial
     */
    public boolean isArtificial() {
        return artificial;
    }

    /**
     * Indicates whether the player is still active in the game.
     *
     * @return {@code true} if the player is active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets whether the player is active in the game.
     *
     * @param active active status
     */
    public void setActive(boolean active) {
        this.active = active;
    }
}
