package com.cincuentazo.model;

import java.util.Optional;

/**
 * Define la estrategia que usa un jugador artificial para escoger una carta.
 */
public interface ThreadStrategy {

    /**
     * Escoge una jugada válida para un jugador artificial.
     *
     * @param player jugador artificial
     * @param currentSum suma actual de la mesa
     * @return jugada escogida, o vacío si no hay jugada posible
     */
    Optional<CardPlay> choosePlay(Player player, int currentSum);
}