package com.example.hackathon2024.model.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RoleDto {
    @NotBlank(message = "El nombre es requerido")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "El nombre del rol solo puede contener letras")
    private String name;
}
