package com.cincuentazo.exceptions;

/**
 * Checked exception thrown when the initial game configuration is invalid.
 */
public class GameConfigurationException extends CincuentazoException {

    /**
     * Creates a configuration exception without a message.
     */
    public GameConfigurationException() {
        super();
    }

    /**
     * Creates a configuration exception with a message.
     *
     * @param message descriptive exception message
     */
    public GameConfigurationException(String message) {
        super(message);
    }

    /**
     * Creates a configuration exception with a message and cause.
     *
     * @param message descriptive exception message
     * @param cause original cause of the exception
     */
    public GameConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a configuration exception with a cause.
     *
     * @param cause original cause of the exception
     */
    public GameConfigurationException(Throwable cause) {
        super(cause);
    }
}
