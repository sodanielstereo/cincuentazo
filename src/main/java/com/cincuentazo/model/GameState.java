package com.cincuentazo.model;

/**
 * Represents the possible states of a Cincuentazo match.
 */
public enum GameState {

    /** Game has been created but not yet started. */
    NOT_STARTED,

    /** Waiting for the human player to play a card. */
    WAITING_REAL_PLAYER_CARD,

    /** Waiting for the human player to draw a card from the deck. */
    WAITING_REAL_PLAYER_DRAW,

    /** It is an artificial player's turn to play a card. */
    ARTIFICIAL_PLAYER_TURN,

    /** An artificial player must draw a card from the deck. */
    ARTIFICIAL_PLAYER_DRAW,

    /** The game has finished and a winner has been declared. */
    FINISHED
}
