package com.tfg.authservice.auth.model.loader;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.tfg.authservice.auth.model.VO.Role;
import com.tfg.authservice.auth.model.VO.Role.RoleName;
import com.tfg.authservice.auth.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        if (roleRepository.findByRoleName(RoleName.ROLE_DEVELOPER).isEmpty()) {
            Role developerRole = Role.builder()
                .roleName(RoleName.ROLE_DEVELOPER)
                .build();
            roleRepository.save(developerRole);
        }
    }
}
