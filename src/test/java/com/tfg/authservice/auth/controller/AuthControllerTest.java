package com.tfg.authservice.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tfg.authservice.auth.dto.AuthResponse;
import com.tfg.authservice.auth.dto.LoginRequest;
import com.tfg.authservice.auth.dto.RegisterRequest;
import com.tfg.authservice.auth.service.AuthService;
import com.tfg.authservice.config.SecurityConfig;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)  // Necesario pues se carga la config de seguridad en el test
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private PasswordEncoder passwordEncoder; // Spring Boot necesita un PasswordEncoder


    @Autowired
    private ObjectMapper objectMapper; // Para convertir objetos a JSON


    // TEST PARA 200 
    @Test
    void register_ShouldReturnAuthResponse() throws Exception {
        AuthResponse authResponse = new AuthResponse("fake-token", "testuser", "test@example.com");

        Mockito.when(authService.register(any(RegisterRequest.class)))
               .thenReturn(authResponse);

        RegisterRequest request = new RegisterRequest();
        request.setUsername("testuser");
        request.setEmail("test@example.com");
        request.setPassword("1234");

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("fake-token"))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void login_ShouldReturnAuthResponse() throws Exception {
        AuthResponse authResponse = new AuthResponse("fake-token", "testuser", "test@example.com");

        Mockito.when(authService.login(any(LoginRequest.class)))
               .thenReturn(authResponse);

        LoginRequest request = new LoginRequest("testuser", "1234");

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("fake-token"))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    // TEST PARA 400 Bad Request
}
