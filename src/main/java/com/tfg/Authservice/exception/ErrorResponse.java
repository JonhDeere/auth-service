package com.tfg.authservice.exception;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * Modelo de respuesta para manejar errores en la API.
 * 
 * Este objeto proporciona detalles sobre un error ocurrido en el sistema,
 * incluyendo código de estado, mensaje y el momento en que ocurrió.
 * Tipo:
    {
        "timestamp": "2025-04-28T18:25:43.511",
        "status": 409,
        "error": "Conflict",
        "message": "Username is already taken",
        "path": "/auth/register"
    }
 *    
 */
@Data
public class ErrorResponse {

    /**
     * Marca temporal de cuando ocurrió el error.
     */
    private LocalDateTime timestamp;

    /**
     * Código de estado HTTP asociado al error.
     */
    private int status;

    /**
     * Tipo de error ocurrido (por ejemplo, "Bad Request", "Internal Server Error").
     */
    private String error;

    /**
     * Mensaje descriptivo con detalles sobre el error.
     */
    private String message;

    /**
     * Ruta del endpoint donde ocurrió el error.
     */
    private String path;

    /**
     * Constructor que inicializa el objeto de error con los detalles proporcionados.
     *
     * @param status Código de estado HTTP del error.
     * @param error Tipo de error (nombre).
     * @param message Descripción del error.
     * @param path Ruta de la solicitud donde ocurrió el error.
     */
    public ErrorResponse(int status, String error, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}

