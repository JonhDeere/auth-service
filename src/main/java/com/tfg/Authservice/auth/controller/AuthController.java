package com.tfg.Authservice.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.Authservice.auth.dto.AuthResponse;
import com.tfg.Authservice.auth.dto.LoginRequest;
import com.tfg.Authservice.auth.dto.RegisterRequest;
import com.tfg.Authservice.auth.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor // Inyecta AuthService automáticamente (lombok)
public class AuthController {

    private AuthService authService; 

    
    /**
     * Endpoint para registrar un nuevo usuario.
     * @param registerRequest Contiene los datos del nuevo usuario.
     * @return Respuesta con el token de autenticación.
     * ResponseEntity:	Devuelve respuestas HTTP bien formadas
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
    
}
