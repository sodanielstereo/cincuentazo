package com.cincuentazo.exceptions;

/**
 * Excepción propia marcada que se lanza cuando la configuración inicial del juego no es válida.
 */
public class GameConfigurationException extends CincuentazoException {

    /**
     * Crea una excepción de configuración sin mensaje.
     */
    public GameConfigurationException() {
        super();
    }

    /**
     * Crea una excepción de configuración con mensaje.
     *
     * @param message mensaje descriptivo de la excepción
     */
    public GameConfigurationException(String message) {
        super(message);
    }

    /**
     * Crea una excepción de configuración con mensaje y causa.
     *
     * @param message mensaje descriptivo de la excepción
     * @param cause causa original de la excepción
     */
    public GameConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Crea una excepción de configuración con causa.
     *
     * @param cause causa original de la excepción
     */
    public GameConfigurationException(Throwable cause) {
        super(cause);
    }
}