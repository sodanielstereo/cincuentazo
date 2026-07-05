package com.cincuentazo.exceptions;

/**
 * Excepción propia marcada que se lanza cuando el mazo no tiene cartas disponibles.
 */
public class EmptyDeckException extends CincuentazoException {

    /**
     * Crea una excepción de mazo vacío sin mensaje.
     */
    public EmptyDeckException() {
        super();
    }

    /**
     * Crea una excepción de mazo vacío con mensaje.
     *
     * @param message mensaje descriptivo de la excepción
     */
    public EmptyDeckException(String message) {
        super(message);
    }

    /**
     * Crea una excepción de mazo vacío con mensaje y causa.
     *
     * @param message mensaje descriptivo de la excepción
     * @param cause causa original de la excepción
     */
    public EmptyDeckException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Crea una excepción de mazo vacío con causa.
     *
     * @param cause causa original de la excepción
     */
    public EmptyDeckException(Throwable cause) {
        super(cause);
    }
}