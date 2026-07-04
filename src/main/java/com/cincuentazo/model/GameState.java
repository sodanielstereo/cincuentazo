package com.cincuentazo.model;

/**
 * Representa los estados posibles de una partida de Cincuentazo.
 */
public enum GameState {
    NOT_STARTED,
    WAITING_REAL_PLAYER_CARD,
    WAITING_REAL_PLAYER_DRAW,
    ARTIFICIAL_PLAYER_TURN,
    ARTIFICIAL_PLAYER_DRAW,
    FINISHED
}