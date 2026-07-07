package com.cincuentazo.exceptions;

/**
 * Checked exception thrown when the deck has no cards available.
 */
public class EmptyDeckException extends CincuentazoException {

    /**
     * Creates an empty deck exception without a message.
     */
    public EmptyDeckException() {
        super();
    }

    /**
     * Creates an empty deck exception with a message.
     *
     * @param message descriptive exception message
     */
    public EmptyDeckException(String message) {
        super(message);
    }

    /**
     * Creates an empty deck exception with a message and cause.
     *
     * @param message descriptive exception message
     * @param cause original cause of the exception
     */
    public EmptyDeckException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates an empty deck exception with a cause.
     *
     * @param cause original cause of the exception
     */
    public EmptyDeckException(Throwable cause) {
        super(cause);
    }
}
