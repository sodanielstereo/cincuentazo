package com.cincuentazo.model;

import java.util.ArrayList;
import java.util.List;

import com.cincuentazo.exceptions.InvalidMoveException;

/**
 * Representa un jugador del juego Cincuentazo.
 */
public abstract class Player {

    public static final int MAX_HAND_SIZE = 4;

    private final String name;
    private final boolean artificial;
    private final List<Card> hand;
    private boolean active;

    /**
     * Crea un jugador.
     *
     * @param name nombre del jugador
     * @param artificial indica si el jugador es artificial
     */
    protected Player(String name, boolean artificial) {
        this.name = name;
        this.artificial = artificial;
        this.hand = new ArrayList<>();
        this.active = true;
    }

    /**
     * Agrega una carta a la mano del jugador.
     *
     * @param card carta a agregar
     */
    public void addCard(Card card) {
        if (hand.size() >= MAX_HAND_SIZE) {
            throw new InvalidMoveException("El jugador ya tiene 4 cartas en la mano.");
        }

        hand.add(card);
    }

    /**
     * Quita una carta de la mano según su índice.
     *
     * @param index posición de la carta
     * @return carta removida
     */
    public Card removeCardAt(int index) {
        if (index < 0 || index >= hand.size()) {
            throw new InvalidMoveException("La posición de la carta no es válida.");
        }

        return hand.remove(index);
    }

    /**
     * Quita una carta específica de la mano.
     *
     * @param card carta a remover
     * @return true si la carta fue removida
     */
    public boolean removeCard(Card card) {
        return hand.remove(card);
    }

    /**
     * Verifica si el jugador tiene al menos una carta jugable.
     *
     * @param currentSum suma actual de la mesa
     * @return true si tiene carta jugable
     */
    public boolean hasPlayableCard(int currentSum) {
        return hand.stream().anyMatch(card -> card.canBePlayed(currentSum));
    }

    /**
     * Retira todas las cartas de la mano.
     *
     * @return cartas que tenía el jugador
     */
    public List<Card> removeAllCards() {
        List<Card> removedCards = new ArrayList<>(hand);
        hand.clear();
        return removedCards;
    }

    /**
     * Retorna una copia de la mano del jugador.
     *
     * @return mano del jugador
     */
    public List<Card> getHand() {
        return new ArrayList<>(hand);
    }

    public String getName() {
        return name;
    }

    public boolean isArtificial() {
        return artificial;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}