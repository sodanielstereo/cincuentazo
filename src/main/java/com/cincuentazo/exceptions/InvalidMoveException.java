package com.cincuentazo.exceptions;

/**
 * Unchecked exception thrown when a player performs an invalid action.
 */
public class InvalidMoveException extends RuntimeException {

    /**
     * Creates an invalid move exception without a message.
     */
    public InvalidMoveException() {
        super();
    }

    /**
     * Creates an invalid move exception with a message.
     *
     * @param message descriptive exception message
     */
    public InvalidMoveException(String message) {
        super(message);
    }

    /**
     * Creates an invalid move exception with a message and cause.
     *
     * @param message descriptive exception message
     * @param cause original cause of the exception
     */
    public InvalidMoveException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates an invalid move exception with a cause.
     *
     * @param cause original cause of the exception
     */
    public InvalidMoveException(Throwable cause) {
        super(cause);
    }
}
