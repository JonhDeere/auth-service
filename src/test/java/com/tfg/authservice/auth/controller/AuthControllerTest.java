package com.tfg.authservice.auth.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

// Permite la inyeccion de depndencias simuladas sin acceder a la BD (@Mock)
@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @InjectMocks // Insyecta el controlador a probar (AuthController) y los Mocks necesarios
    private AuthController authController;




    

}
