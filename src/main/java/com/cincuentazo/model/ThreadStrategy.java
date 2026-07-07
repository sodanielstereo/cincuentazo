package com.cincuentazo.model;

import java.util.Optional;

/**
 * Defines the strategy used by an artificial player to choose a card.
 */
public interface ThreadStrategy {

    /**
     * Chooses a valid play for an artificial player.
     *
     * @param player artificial player
     * @param currentSum current table sum
     * @return chosen play, or empty if no play is possible
     */
    Optional<CardPlay> choosePlay(Player player, int currentSum);
}
