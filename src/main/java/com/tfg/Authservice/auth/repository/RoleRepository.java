package com.tfg.authservice.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tfg.authservice.auth.model.VO.Role;
import com.tfg.authservice.auth.model.VO.Role.RoleName;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario
    // Por ejemplo, encontrar un rol por su nombre
    Optional<Role> findByRoleName(RoleName name);

}
