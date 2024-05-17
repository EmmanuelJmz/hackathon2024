package com.example.hackathon2024.service.role;

import com.example.hackathon2024.model.role.Role;
import com.example.hackathon2024.model.role.RoleDto;
import com.example.hackathon2024.model.role.RoleRepository;
import com.example.hackathon2024.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository repository;

    @Value("${client.name}")
    private String clientName;

    @Transactional(rollbackFor = {SQLException.class})
    public ApiResponse<Role> create (RoleDto role) {
        Role foundRole = repository.findByName(role.getName()).orElse(null);
        if (foundRole != null) {
            return new ApiResponse<>(
                    foundRole, true, HttpStatus.BAD_REQUEST, "El rol ingresado ya esta registrado"
            );
        }
        Role newRole = new Role();
        String id = UUID.randomUUID().toString();
        newRole.setId(id);
        newRole.setName(role.getName());
        Role saveRole = repository.save(newRole);
        return new ApiResponse<>(
                saveRole, false, HttpStatus.OK, "Rol registrado correctamente"
        );
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ApiResponse<Role> createUser (Role role) {
        Role foundRole = repository.findByName(clientName).orElse(null);
        if(foundRole != null) {
            return new ApiResponse<>(
                    foundRole, true, HttpStatus.BAD_REQUEST, "El rol cliente ya esta registrado"
            );
        }
        String id = UUID.randomUUID().toString();
        role.setId(id);
        Role newRole = repository.save(role);
        return new ApiResponse<>(
                newRole, false, HttpStatus.OK, "Usuario rol registrado correctamente"
        );
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<Role>> getAll () {
        List<Role> roles = repository.findAll();
        if (roles.isEmpty()) {
            return new ApiResponse<>(
                    roles, true ,HttpStatus.BAD_REQUEST, "No hay roles registrados"
            );
        } else {
            return new ApiResponse<>(
                    roles, false, HttpStatus.OK, "Roles encontrados"
            );
        }
    }

    @Transactional(readOnly = true)
    public ApiResponse<Role> getById (String id) {
        Optional<Role> optionalRole = repository.findById(id);
        if (optionalRole.isPresent()) {
            Role role = optionalRole.get();
            return new ApiResponse<>(
                    role, false, HttpStatus.OK, "Rol encontrado"
            );
        } else {
            return new ApiResponse<>(
                    null, true, HttpStatus.BAD_REQUEST, "Rol no encontrado"
            );
        }
    }
}
