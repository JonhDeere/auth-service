package com.tfg.authservice.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfg.authservice.auth.model.VO.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario
    // Por ejemplo, encontrar un usuario por su nombre de usuario o correo electrónico
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

}
