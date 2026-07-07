package com.cincuentazo.threads;

import java.util.concurrent.ThreadLocalRandom;

import javafx.application.Platform;

/**
 * Thread that waits between 2 and 4 seconds before an artificial player
 * draws a card from the deck.
 */
public class ArtificialDrawThread extends Thread {

    /** Minimum waiting time in milliseconds. */
    private static final int MIN_WAITING_TIME = 2000;

    /** Maximum waiting time in milliseconds. */
    private static final int MAX_WAITING_TIME = 4000;

    private final Runnable drawAction;

    /**
     * Creates a thread to simulate the time an artificial player takes
     * to draw a card from the deck.
     *
     * @param drawAction action to execute after the wait
     */
    public ArtificialDrawThread(Runnable drawAction) {
        this.drawAction = drawAction;
    }

    /**
     * Runs the thread, waits a random amount of time, and then dispatches
     * the action to the JavaFX application thread.
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
