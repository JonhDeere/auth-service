package com.tfg.Authservice.auth.service;


import com.tfg.Authservice.auth.model.VO.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tfg.Authservice.auth.dto.AuthResponse;
import com.tfg.Authservice.auth.dto.LoginRequest;
import com.tfg.Authservice.auth.dto.RegisterRequest;
import com.tfg.Authservice.auth.model.VO.User;
import com.tfg.Authservice.auth.repository.RoleRepository;
import com.tfg.Authservice.auth.repository.UserRepository;
import com.tfg.Authservice.auth.security.JwtProvider;

import java.util.Collections; 

import lombok.RequiredArgsConstructor;

import static com.tfg.Authservice.auth.model.VO.Role.RoleName;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username is already taken");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already in use");
        }

        Role defaultRole = roleRepository.findByName(RoleName.ROLE_DEVELOPER)
                .orElseThrow(() -> new RuntimeException("Default role not found"));

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

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        String token = jwtProvider.generateToken(user.getUsername());
        return new AuthResponse(token, user.getUsername(), user.getEmail());
    }

}
