package com.cincuentazo.exceptions;

/**
 * Excepción propia base del juego Cincuentazo.
 * Representa una condición excepcional general dentro de la lógica del juego.
 */
public class CincuentazoException extends Exception {

    /**
     * Crea una excepción sin mensaje.
     */
    public CincuentazoException() {
        super();
    }

    /**
     * Crea una excepción con mensaje.
     *
     * @param message mensaje descriptivo de la excepción
     */
    public CincuentazoException(String message) {
        super(message);
    }

    /**
     * Crea una excepción con mensaje y causa.
     *
     * @param message mensaje descriptivo de la excepción
     * @param cause causa original de la excepción
     */
    public CincuentazoException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Crea una excepción con causa.
     *
     * @param cause causa original de la excepción
     */
    public CincuentazoException(Throwable cause) {
        super(cause);
    }
}