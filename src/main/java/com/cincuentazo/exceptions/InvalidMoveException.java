package com.cincuentazo.exceptions;

/**
 * Excepción propia no marcada que se lanza cuando un jugador realiza una acción inválida.
 */
public class InvalidMoveException extends RuntimeException {

    /**
     * Crea una excepción de movimiento inválido sin mensaje.
     */
    public InvalidMoveException() {
        super();
    }

    /**
     * Crea una excepción de movimiento inválido con mensaje.
     *
     * @param message mensaje descriptivo de la excepción
     */
    public InvalidMoveException(String message) {
        super(message);
    }

    /**
     * Crea una excepción de movimiento inválido con mensaje y causa.
     *
     * @param message mensaje descriptivo de la excepción
     * @param cause causa original de la excepción
     */
    public InvalidMoveException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Crea una excepción de movimiento inválido con causa.
     *
     * @param cause causa original de la excepción
     */
    public InvalidMoveException(Throwable cause) {
        super(cause);
    }
}