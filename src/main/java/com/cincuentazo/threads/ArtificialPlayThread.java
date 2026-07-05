package com.cincuentazo.threads;

import java.util.concurrent.ThreadLocalRandom;

import javafx.application.Platform;

/**
 * Hilo encargado de esperar entre 2 y 4 segundos antes de ejecutar
 * la jugada de un jugador artificial.
 */
public class ArtificialPlayThread extends Thread {

    private static final int MIN_WAITING_TIME = 2000;
    private static final int MAX_WAITING_TIME = 4000;

    private final Runnable playAction;

    /**
     * Crea un hilo para simular el tiempo de decisión del jugador artificial.
     *
     * @param playAction acción que se ejecutará después de la espera
     */
    public ArtificialPlayThread(Runnable playAction) {
        this.playAction = playAction;
    }

    /**
     * Ejecuta el hilo, espera un tiempo aleatorio y luego envía la acción
     * al hilo principal de JavaFX.
     */
    @Override
    public void run() {
        try {
            int waitingTime = ThreadLocalRandom.current()
                    .nextInt(MIN_WAITING_TIME, MAX_WAITING_TIME + 1);

            Thread.sleep(waitingTime);

            Platform.runLater(playAction);

        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }
}