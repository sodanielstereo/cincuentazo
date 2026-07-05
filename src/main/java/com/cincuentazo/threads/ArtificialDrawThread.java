package com.cincuentazo.threads;

import java.util.concurrent.ThreadLocalRandom;

import javafx.application.Platform;

/**
 * Hilo encargado de esperar entre 2 y 4 segundos antes de que
 * un jugador artificial tome una carta del mazo.
 */
public class ArtificialDrawThread extends Thread {

    private static final int MIN_WAITING_TIME = 2000;
    private static final int MAX_WAITING_TIME = 4000;

    private final Runnable drawAction;

    /**
     * Crea un hilo para simular el tiempo que tarda el jugador artificial
     * en tomar una carta del mazo.
     *
     * @param drawAction acción que se ejecutará después de la espera
     */
    public ArtificialDrawThread(Runnable drawAction) {
        this.drawAction = drawAction;
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

            Platform.runLater(drawAction);

        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }
}