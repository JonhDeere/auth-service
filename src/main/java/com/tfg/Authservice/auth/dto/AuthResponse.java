package com.tfg.authservice.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Se envía al frontend tras login o registro exitoso. 
 * El token JWT irá en el header Authorization en futuras peticiones.
 */

@Data
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private String username;
    private String email;

}
