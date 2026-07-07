package com.cincuentazo.model;

/**
 * Represents the human player in the game.
 */
public class RealPlayer extends Player {

    /**
     * Creates a human player.
     *
     * @param name player name
     */
    public RealPlayer(String name) {
        super(name, false);
    }
}
