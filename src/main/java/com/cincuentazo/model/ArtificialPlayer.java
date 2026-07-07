package com.cincuentazo.model;

import java.util.Optional;

/**
 * Represents an artificial player in the game.
 */
public class ArtificialPlayer extends Player {

    private final ThreadStrategy strategy;

    /**
     * Creates an artificial player with a play strategy.
     *
     * @param name artificial player name
     * @param strategy strategy used to choose cards
     */
    public ArtificialPlayer(String name, ThreadStrategy strategy) {
        super(name, true);
        this.strategy = strategy;
    }

    /**
     * Chooses a play using the artificial player's strategy.
     *
     * @param currentSum current table sum
     * @return chosen play, or empty if no play is possible
     */
    public Optional<CardPlay> choosePlay(int currentSum) {
        return strategy.choosePlay(this, currentSum);
    }

    /**
     * Returns the artificial player's strategy.
     *
     * @return strategy in use
     */
    public ThreadStrategy getStrategy() {
        return strategy;
    }
}
