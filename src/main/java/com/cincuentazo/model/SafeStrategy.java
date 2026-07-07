package com.cincuentazo.model;

import java.util.Optional;

/**
 * Safe strategy for artificial players.
 * Chooses the card that leaves the highest sum without exceeding 50.
 */
public class SafeStrategy implements ThreadStrategy {

    /**
     * Chooses the best playable card.
     *
     * @param player artificial player
     * @param currentSum current table sum
     * @return chosen play, or empty if no play is possible
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
