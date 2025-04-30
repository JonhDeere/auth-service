package com.tfg.authservice.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;


/**
 * Manejador global de excepciones para el servicio de autenticación.
 * 
 * Esta clase captura y maneja las excepciones lanzadas en toda la aplicación,
 *  proporcionando respuestas uniformes y estructuradas en caso de error.
 * Cualquier excepción: 
 *                  Personalizada (CustomException) 
 *                  De validación (@Valid) 
 *                  Inesperada (Exception general)) 
 *  será  interceptada y convertida en una respuesta clara y estandarizada, 
 *  con código, mensaje y path.
 */
// Intercepta globalmente en controladores REST sin necesidad de inyectar
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja nuestras excepciones personalizadas del sistema (CustomException).
     * 
     * @param ex Excepción personalizada lanzada en la aplicación.
     * @param request Información de la solicitud que generó el error.
     * @return Respuesta estructurada con código de estado y mensaje de error.
     */
    // Anotación que indica que este método manejará excepciones de tipo CustomException
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(
            CustomException ex,  // Excepción que ha sido lanzada
            HttpServletRequest request // Información sobre la solicitud HTTP en la que ocurrió el error
            ) 
    {
        // Creación de una respuesta de error con los detalles de la excepción
        ErrorResponse response = new ErrorResponse(
                ex.getStatus().value(), // Código de estado HTTP (por ejemplo, 404, 500) obtenido de mi CustomException
                ex.getStatus().getReasonPhrase(), // Descripción textual del código de estado (Ejemplo: "Not Found")
                ex.getMessage(), // Mensaje detallado sobre la causa del error
                request.getRequestURI() // URI de la solicitud donde ocurrió el error
        );

        // Devuelve la respuesta de error encapsulada en un ResponseEntity junto con el código de estado HTTP correspondiente
        return new ResponseEntity<>(response, ex.getStatus());
    }


    /**
     * Maneja excepciones de validación generadas por @Valid.
     * 
     * @param ex Excepción lanzada cuando un argumento no es válido.
     * @param request Información de la solicitud que generó el error.
     * @return Respuesta con código de estado HTTP 400 y detalles de validación.
     */
    // Anotacion para manejar excepciones generadas por validaciones con @Valid en los controladores
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex, // Excepción capturada cuando un argumento no cumple las reglas de validación
            HttpServletRequest request // Información sobre la solicitud HTTP en la que ocurrió el error
            ) 
    {
        // Extrae los mensajes de error de la validación
        String errorMessage = ex.getBindingResult() // Obtiene el resultado de la validación
                .getAllErrors() // Obtiene todos los errores generados
                .stream() // Convierte la lista en un flujo de datos
                .map(err -> err.getDefaultMessage()) // Extrae el mensaje de cada error
                .collect(Collectors.joining(", ")); // Une los mensajes en una sola cadena separada por comas

        // Crea una respuesta de error estructurada con los detalles
        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(), // Código de estado HTTP 400 (Bad Request) asignado de HttpStatus manualmente
                HttpStatus.BAD_REQUEST.getReasonPhrase(), // Descripción textual del código de estado ("Bad Request")
                errorMessage, // Mensaje con la lista de errores encontrados en la validación
                request.getRequestURI() // URI de la solicitud donde ocurrió el error
        );

        // Devuelve la respuesta encapsulada en un ResponseEntity junto con el código de estado HTTP 400
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    /**
     * Maneja cualquier otra excepción no controlada.
     * 
     * @param ex Excepción inesperada lanzada en la aplicación.
     * @param request Información de la solicitud que generó el error.
     * @return Respuesta con código de estado HTTP 500 y mensaje genérico de error.
     */
    // Maneja cualquier excepción que no haya sido capturada por los métodos anteriores
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(
            Exception ex,  // Excepción inesperada ocurrida en cualquier parte de la aplicación
            HttpServletRequest request // Información sobre la solicitud HTTP en la que ocurrió el error
            ) 
    {
        // Crea un objeto de respuesta de error con detalles generales sobre el fallo
        ErrorResponse response = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), // Código de estado HTTP 500 (Error interno del servidor)
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), // Descripción textual del código de estado ("Internal Server Error")
                "Unexpected error occurred", // Mensaje genérico para errores no manejados
                request.getRequestURI() // URI de la solicitud donde ocurrió el error
        );

        // Devuelve la respuesta encapsulada en un ResponseEntity con el código de estado 500
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
