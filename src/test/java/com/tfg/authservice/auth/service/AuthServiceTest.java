package com.tfg.authservice.auth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tfg.authservice.auth.dto.AuthResponse;
import com.tfg.authservice.auth.dto.RegisterRequest;
import com.tfg.authservice.auth.model.VO.Role;
import com.tfg.authservice.auth.model.VO.User;
import com.tfg.authservice.auth.model.VO.Role.RoleName;
import com.tfg.authservice.auth.repository.RoleRepository;
import com.tfg.authservice.auth.repository.UserRepository;
import com.tfg.authservice.auth.security.JwtProvider;

/**
 * Test class para servicio de autenticación: AuthService (JUnit, Mockito).
 * Verificar si el registro de usuario funciona correctamente.
 * 
 * 1. Si el username y el email no existen, debe:
 *   Encriptar la contraseña
 *   Guardar el usuario
 *   Generar un token válido
 *   Retornar AuthResponse con esos datos
* 
* 2. Si el username ya existe → excepción
*
* 3. Si el email ya existe → excepción
 */

 // Permite la inyección de dependencias simuladas sin acceder a BD (@Mock).
@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks // Inyecta el servicio a probar (AuthService) y los mocks necesarios
    private AuthService authService; // Servicio a probar (a inyectar)

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtProvider jwtProvider; 

    private RegisterRequest request;

    @BeforeEach
    void setUp() {
        // Inicializar mocks y otras configuraciones necesarias para las pruebas
        request = new RegisterRequest();
        request.setUsername("testuser");
        request.setEmail("test@example.com");
        request.setPassword("1234");
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        // Aquí irían las aserciones y verificaciones necesarias para comprobar el registro exitoso
        // Por ejemplo, verificar que se llama a los métodos de los mocks con los parámetros correctos
        // y que se genera un token válido.
        Role developerRole = new Role(1L, RoleName.ROLE_DEVELOPER);  // Role ficticio para registrarse

        // Se simula que el usuario no existe en la base de datos
        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);

        // Se simula que el rol ROLE_DEVELOPER existe y se obtiene correctamente
        when(roleRepository.findByRoleName(RoleName.ROLE_DEVELOPER)).thenReturn(Optional.of(developerRole));

        // Simula la encriptacion de la contraseña
        when(passwordEncoder.encode("1234")).thenReturn("encrypted");

        // Simula la generación del token JWT
        when(jwtProvider.generateToken(Mockito.eq("testuser"))).thenReturn("fake-token");

        // Ejecuta la peticion en el metodo register y guarda el resultado
        AuthResponse response = authService.register(request);

        // MODULO DE LA RESPUESTA:
        assertNotNull(response);                                    // Validamos que la respuesta no sea nula
        assertEquals("testuser", response.getUsername()); // Validamos que contenga el usuario correcto
        assertEquals("test@example.com", response.getEmail()); // Validamos que contenga el email correcto
        assertEquals("fake-token", response.getToken()); // Validamos que contenga el token generado

        // Por utlimo verificamos que el usuario se guarda en la BD (any: engloba cualquier instancia de la clase)
        // Comprueba que el metodo del objeto simulado, ha sido llamado...
        verify(userRepository).save(any(User.class)); // 
    }
}
