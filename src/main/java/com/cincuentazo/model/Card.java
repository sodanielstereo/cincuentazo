package com.cincuentazo.model;

import java.util.List;

/**
 * Representa una carta de la baraja usada en el juego Cincuentazo.
 */
public class Card {

    private final Value value;
    private final Suit suit;

    /**
     * Crea una carta con valor y palo.
     *
     * @param value valor de la carta
     * @param suit palo de la carta
     */
    public Card(Value value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }

    /**
     * Retorna los posibles valores numéricos de la carta según las reglas del juego.
     * El As puede valer 1 o 10.
     *
     * @return lista de posibles valores de la carta
     */
    public List<Integer> getPossibleValues() {
        return switch (value) {
            case TWO -> List.of(2);
            case THREE -> List.of(3);
            case FOUR -> List.of(4);
            case FIVE -> List.of(5);
            case SIX -> List.of(6);
            case SEVEN -> List.of(7);
            case EIGHT -> List.of(8);
            case NINE -> List.of(0);
            case TEN -> List.of(10);
            case JACK, QUEEN, KING -> List.of(-10);
            case ACE -> List.of(1, 10);
        };
    }

    /**
     * Retorna el valor usado cuando la carta inicia la suma de la mesa.
     * El As inicia con valor 1.
     *
     * @return valor inicial de la carta
     */
    public int getInitialValue() {
        if (value == Value.ACE) {
            return 1;
        }

        return getPossibleValues().get(0);
    }

    /**
     * Verifica si la carta puede jugarse sin superar 50.
     *
     * @param currentSum suma actual de la mesa
     * @return true si la carta puede jugarse, false en caso contrario
     */
    public boolean canBePlayed(int currentSum) {
        return getPossibleValues()
                .stream()
                .anyMatch(cardValue -> currentSum + cardValue <= 50);
    }

    /**
     * Obtiene el mejor valor jugable de la carta.
     * Para el As, escoge 10 si no supera 50; de lo contrario escoge 1.
     *
     * @param currentSum suma actual de la mesa
     * @return mejor valor posible de la carta
     */
    public int getBestPlayableValue(int currentSum) {
        return getPossibleValues()
                .stream()
                .filter(cardValue -> currentSum + cardValue <= 50)
                .max(Integer::compareTo)
                .orElseThrow(() -> new IllegalStateException("La carta no puede ser jugada sin superar 50."));
    }

    /**
     * Retorna el valor de la carta.
     *
     * @return valor de la carta
     */
    public Value getValue() {
        return value;
    }

    /**
     * Retorna el palo de la carta.
     *
     * @return palo de la carta
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Retorna la representación visual de la carta.
     *
     * @return texto de la carta
     */
    @Override
    public String toString() {
        return value.getSymbol() + suit.getSymbol();
    }
}