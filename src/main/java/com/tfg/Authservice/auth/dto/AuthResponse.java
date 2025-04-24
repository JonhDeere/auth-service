package com.tfg.Authservice.auth.dto;

import lombok.AllArgsConstructor;

/**
 * Se envía al frontend tras login o registro exitoso. 
 * El token JWT irá en el header Authorization en futuras peticiones.
 */

@AllArgsConstructor
public class AuthResponse {

    private String token;
    private String username;
    private String email;

}
