package com.cincuentazo.exceptions;

/**
 * Base checked exception for the Cincuentazo game.
 * Represents a general exceptional condition within the game logic.
 */
public class CincuentazoException extends Exception {

    /**
     * Creates an exception without a message.
     */
    public CincuentazoException() {
        super();
    }

    /**
     * Creates an exception with a descriptive message.
     *
     * @param message descriptive exception message
     */
    public CincuentazoException(String message) {
        super(message);
    }

    /**
     * Creates an exception with a message and cause.
     *
     * @param message descriptive exception message
     * @param cause original cause of the exception
     */
    public CincuentazoException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates an exception with a cause.
     *
     * @param cause original cause of the exception
     */
    public CincuentazoException(Throwable cause) {
        super(cause);
    }
}
