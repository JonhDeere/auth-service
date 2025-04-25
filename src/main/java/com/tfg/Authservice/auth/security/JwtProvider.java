package com.tfg.authservice.auth.security;

import java.security.Key;
import java.util.Date;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

/**
 * Se utiliza para generar y validar tokens JWT.
 * 
 * @param jwtSecret clave secreta para firmar el token
 * @param jwtExpiration tiempo de expiración del token en milisegundos
 */

@Component
public class JwtProvider {

    /**
     * Obtienen valores desde application.properties para definir la clave secreta y 
        el tiempo de expiración del token.
    */
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    // Almacenará la clave secreta para firmar el token JWT
    // Se utiliza un objeto Key para representar la clave secreta de forma segura
    private Key secretKey;

    @PostConstruct  // Método que se ejecuta después de la construcción del bean
    // Se utiliza para inicializar la clave secreta a partir de la propiedad jwtSecret
    public void init() {
        // Inicializa la clave secreta para firmar el token JWT
        this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }


    public String generateToken(String username) 
    {
        Date now = new Date();
        Date experyDate = new Date(now.getTime() + jwtExpiration);

        // Crea un token JWT utilizando la clave secreta y la fecha de expiración
        return Jwts.builder()
                .setSubject(username) // Establece el nombre de usuario como sujeto del token
                .setIssuedAt(experyDate) // Establece la fecha de emisión del token
                .setExpiration(experyDate) // Establece la fecha de expiración del token
                .signWith(secretKey, SignatureAlgorithm.HS256) // Firma el token con la clave secreta y el algoritmo HS256
                .compact(); // Genera el token JWT como una cadena compacta
    }

    public String getUserNameFromToken(String token) {
        // Extrae el nombre de usuario del token JWT utilizando la clave secreta
        return Jwts.parserBuilder()
                .setSigningKey(secretKey) // Establece la clave secreta para verificar la firma del token
                .build()
                .parseClaimsJws(token) // Analiza el token y verifica su firma
                .getBody() // Obtiene el cuerpo del token (claims)
                .getSubject(); // Devuelve el nombre de usuario (sujeto) del token
    }

    public boolean validateToken(String token) {
        try {
            // Verifica la firma del token JWT utilizando la clave secreta
            Jwts.parserBuilder()
                    .setSigningKey(secretKey) // Establece la clave secreta para verificar la firma del token
                    .build()
                    .parseClaimsJws(token); // Analiza el token y verifica su firma
            return true; // Si no se lanza ninguna excepción, el token es válido
        } catch (JwtException | IllegalArgumentException ex) {
            return false; // Si se lanza una excepción, el token no es válido
        }
    }


}
