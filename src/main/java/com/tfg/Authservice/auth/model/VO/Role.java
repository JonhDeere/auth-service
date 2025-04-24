package com.tfg.Authservice.auth.model.VO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;




@Data
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity // Indica que esta clase es una entidad JPA y se mapeará a una tabla en la base de datos
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Se utiliza la estrategia de generación de IDENTITY para que la base de datos genere automáticamente el ID
    private Long Id;

    // Nombre del rol (ADMIN, MANAGER, DEVELOPER)
    // Se utiliza EnumType.STRING para almacenar el nombre del rol como una cadena en la base de datos
    @Enumerated(EnumType.STRING)
    // Se establece que el nombre del rol no puede ser nulo y debe ser único en la base de datos
    @Column(name = "role_name", nullable = false, unique = true) 
    private RoleName roleName;
    

    public enum RoleName {
    
        ROLE_ADMIN,
        ROLE_MANAGER,
        ROLE_DEVELOPER
    }

}
