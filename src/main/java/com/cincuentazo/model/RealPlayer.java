package com.cincuentazo.model;

/**
 * Representa al jugador humano del juego.
 */
public class RealPlayer extends Player {

    /**
     * Crea un jugador humano.
     *
     * @param name nombre del jugador
     */
    public RealPlayer(String name) {
        super(name, false);
    }
}