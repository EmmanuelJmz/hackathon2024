package com.example.hackathon2024.utils;

import com.example.hackathon2024.model.role.Role;
import com.example.hackathon2024.service.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

@Configuration
@RequiredArgsConstructor

public class InitialConfig implements CommandLineRunner {
    private final RoleService roleService;
    Logger logger = Logger.getLogger(InitialConfig.class.getName());

    @Value("${client.name}")
    private String clientName;

    public void run(String... args) {
        try {
            var userRole = Role.builder()
                    .name(clientName)
                    .build();
            ApiResponse<Role> clientResponse = roleService.createUser(userRole);
            if (clientResponse.isError()) {
                logger.warning(clientResponse.getMessage());
            } else {
                logger.info(clientResponse.getMessage());
            }
        } catch (Exception e) {
            logger.info("Error al crear roles");
        }
    }
}
