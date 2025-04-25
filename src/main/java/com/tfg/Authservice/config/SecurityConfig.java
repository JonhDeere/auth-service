package com.tfg.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // Define el algoritmo de encriptación para contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configura las reglas de seguridad
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // CSRF deshabilitado (no hay sesiones)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll() // rutas públicas
                .anyRequest().authenticated()           // lo demás requiere login
            )
            .httpBasic(Customizer.withDefaults()); // por ahora básico, se usará JWT más adelante

        return http.build();
    }
}
