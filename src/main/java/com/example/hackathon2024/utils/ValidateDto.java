package com.example.hackathon2024.utils;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ValidateDto {
    @NotBlank(message = "El token es requerido")
    private String code;

    @NotBlank(message = "El email es requerido")
    private String email;
}
