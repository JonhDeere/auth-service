package com.tfg.authservice.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


/**
 * clase utilizada para transferir datos entre la capa de presentación y 
 *   la lógica de negocio sin exponer directamente las entidades de la base de datos.
 */

/**
 * DTO peticiones de registro.
 * 
 * Este DTO se usará para que el cliente pueda registrar nuevos usuarios.
 * Los mensajes ayudan a validar desde el backend usando @Valid en el controlador.
 * Contiene campos de nombre, email y contrasena.
 * No permite campos en blanco.
 */
@Data
public class RegisterRequest {

    @NotBlank(message = "Username is mandatory")
    private String username;

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;
}
