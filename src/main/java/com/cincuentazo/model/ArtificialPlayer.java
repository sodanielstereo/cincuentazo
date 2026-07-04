package com.cincuentazo.model;

import java.util.Optional;

/**
 * Representa a un jugador artificial del juego.
 */
public class ArtificialPlayer extends Player {

    private final ThreadStrategy strategy;

    /**
     * Crea un jugador artificial con una estrategia de juego.
     *
     * @param name nombre del jugador artificial
     * @param strategy estrategia para escoger cartas
     */
    public ArtificialPlayer(String name, ThreadStrategy strategy) {
        super(name, true);
        this.strategy = strategy;
    }

    /**
     * Escoge una jugada usando la estrategia del jugador artificial.
     *
     * @param currentSum suma actual de la mesa
     * @return jugada escogida, o vacío si no hay jugada posible
     */
    public Optional<CardPlay> choosePlay(int currentSum) {
        return strategy.choosePlay(this, currentSum);
    }

    /**
     * Retorna la estrategia del jugador artificial.
     *
     * @return estrategia usada
     */
    public ThreadStrategy getStrategy() {
        return strategy;
    }
}