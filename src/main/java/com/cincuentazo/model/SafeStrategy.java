package com.cincuentazo.model;

import java.util.Optional;

/**
 * Estrategia segura para el jugador artificial.
 * Escoge la carta que deja la suma más alta sin superar 50.
 */
public class SafeStrategy implements ThreadStrategy {

    /**
     * Escoge la mejor carta jugable.
     *
     * @param player jugador artificial
     * @param currentSum suma actual de la mesa
     * @return jugada escogida
     */
    @Override
    public Optional<CardPlay> choosePlay(Player player, int currentSum) {
        CardPlay bestPlay = null;
        int bestResult = Integer.MIN_VALUE;

        for (Card card : player.getHand()) {
            for (Integer possibleValue : card.getPossibleValues()) {
                int result = currentSum + possibleValue;

                if (result <= 50 && result > bestResult) {
                    bestResult = result;
                    bestPlay = new CardPlay(card, possibleValue);
                }
            }
        }

        return Optional.ofNullable(bestPlay);
    }
}