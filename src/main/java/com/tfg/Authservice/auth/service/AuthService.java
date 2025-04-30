package com.tfg.authservice.auth.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tfg.authservice.auth.dto.AuthResponse;
import com.tfg.authservice.auth.dto.LoginRequest;
import com.tfg.authservice.auth.dto.RegisterRequest;
import com.tfg.authservice.auth.model.VO.Role;
import com.tfg.authservice.auth.model.VO.Role.RoleName;
import com.tfg.authservice.auth.model.VO.User;
import com.tfg.authservice.auth.repository.RoleRepository;
import com.tfg.authservice.auth.repository.UserRepository;
import com.tfg.authservice.auth.security.JwtProvider;
import com.tfg.authservice.exception.CustomException;

import java.util.Collections;

import lombok.RequiredArgsConstructor;

/**
 * Servicio de autenticación para la gestión de usuarios en el sistema.
 * 
 * Proporciona funciones para registrar nuevos usuarios y autenticar usuarios existentes.
 * Implementa validaciones de disponibilidad de username y email, además de la gestión de roles.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;


    
    /**
     * Registra un nuevo usuario en el sistema.
     * 
     * Verifica la disponibilidad del nombre de usuario y correo electrónico,
     * asigna un rol por defecto y almacena la información en la base de datos.
     *
     * @param request Datos de registro del usuario.
     * @return AuthResponse con el token generado y la información del usuario.
     * @throws CustomException Si el nombre de usuario o correo ya están en uso.
     */
    public AuthResponse register(RegisterRequest request) {

        // Verificar que el username no esté repetido
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new CustomException("Username is already taken", HttpStatus.CONFLICT);
        }

        // Verificar que el email no esté ya en uso
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException("Email is already in use", HttpStatus.CONFLICT);
        }

        // Obtener el rol por defecto
        Role defaultRole = roleRepository.findByRoleName(RoleName.ROLE_DEVELOPER)
                .orElseThrow(() -> new CustomException("Default role not found", HttpStatus.INTERNAL_SERVER_ERROR));

        // Crear el nuevo usuario
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singleton(defaultRole))
                .build();

        userRepository.save(user);

        String token = jwtProvider.generateToken(user.getUsername());
        return new AuthResponse(token, user.getUsername(), user.getEmail());
    }


    /**
     * Autentica un usuario en el sistema.
     * 
     * Valida las credenciales proporcionadas comparando el username y la contraseña almacenada.
     *
     * @param request Datos de inicio de sesión del usuario.
     * @return AuthResponse con el token generado y la información del usuario autenticado.
     * @throws CustomException Si el usuario no existe o la contraseña es incorrecta.
     */
    public AuthResponse login(LoginRequest request) {
        // Verificar que el usuario exista
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new CustomException("Invalid username or password", HttpStatus.UNAUTHORIZED));

        // Verificar que la contraseña sea correcta
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }

        String token = jwtProvider.generateToken(user.getUsername());
        return new AuthResponse(token, user.getUsername(), user.getEmail());
    }
}
