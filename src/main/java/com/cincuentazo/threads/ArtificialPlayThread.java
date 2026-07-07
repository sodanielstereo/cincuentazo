package com.cincuentazo.threads;

import java.util.concurrent.ThreadLocalRandom;

import javafx.application.Platform;

/**
 * Thread that waits between 2 and 4 seconds before executing
 * an artificial player's card play.
 */
public class ArtificialPlayThread extends Thread {

    /** Minimum waiting time in milliseconds. */
    private static final int MIN_WAITING_TIME = 2000;

    /** Maximum waiting time in milliseconds. */
    private static final int MAX_WAITING_TIME = 4000;

    private final Runnable playAction;

    /**
     * Creates a thread to simulate the artificial player's decision time.
     *
     * @param playAction action to execute after the wait
     */
    public ArtificialPlayThread(Runnable playAction) {
        this.playAction = playAction;
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

            Platform.runLater(playAction);

        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }
}
