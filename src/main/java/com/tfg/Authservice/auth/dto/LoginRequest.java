package com.tfg.authservice.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


/**
 * clase utilizada para transferir datos entre la capa de presentación y 
 *   la lógica de negocio sin exponer directamente las entidades de la base de datos.
 */

/**
 * DTO peticiones de inicio de sesión.
 * 
 * clase utilizada para autenticar al usuario y devolver el JWT,
 *   sin exponer directamente las entidades de la base de datos.
 * Contiene campos de nombre y contrasena.
 * No permite campos en blanco.
 */
@Data
public class LoginRequest {

    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "Password is mandatory")
    private String password;
}
