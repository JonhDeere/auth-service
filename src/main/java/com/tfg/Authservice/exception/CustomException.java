package com.tfg.authservice.exception;

import org.springframework.http.HttpStatus;



/**
 * Crea Excepción personalizada para manejar errores específicos en el servicio de autenticación.
 * 
 * Esta clase extiende RuntimeException para permitir que la excepción sea lanzada
 * sin necesidad de ser declarada explícitamente en los métodos.
 */
public class CustomException extends RuntimeException {

    /**
     * Código de estado HTTP asociado a la excepción.
     */
    private final HttpStatus status;

    /**
     * Constructor que inicializa la excepción con un mensaje y un código de estado HTTP.
     *
     * @param message Mensaje descriptivo del error.
     * @param status Código de estado HTTP que indica la naturaleza del error.
     */
    public CustomException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    /**
     * Obtiene el código de estado HTTP asociado a la excepción.
     *
     * @return Código de estado HTTP de la excepción.
     */
    public HttpStatus getStatus() {
        return status;
    }
}

