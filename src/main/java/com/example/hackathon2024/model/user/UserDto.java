package com.example.hackathon2024.model.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserDto {

    @Pattern(regexp = "[a-zA-Z ]+$", message = "El nombre no es valido")
    @NotBlank(message = "El nombre es requerido")
    private String fullName;

    @NotBlank(message = "El email es requerido")
    @Email(message = "El email no es valido")
    private String email;

    @NotBlank(message = "La contraseña es requerida")
    @Size(min = 8, message = "La contraseña debe contener al menos 8 caracteres")
    @Size(max = 16, message = "La contraseña no puede contener mas de 16 caracteres")
    private String password;

    @Pattern(regexp = "[a-zA-Z ]+$", message = "El estado no es valido")
    @NotBlank(message = "El estado es requerido")
    private String state;

    @Pattern(regexp = "[a-zA-Z ]+$", message = "La ciudad no es valida")
    @NotBlank(message = "La ciudad es requerida")
    private String city;

    @NotBlank(message = "El telefono es requerido")
    @Pattern(regexp = "\\d+", message = "El telefono no es valido")
    @Size(min = 10, message = "El telefono debe contener al menos 10 caracteres")
    @Size(max = 10, message = "El telefono no puede contener mas de 10 caracteres")
    private String phoneNumber;

    @Pattern(regexp = "[a-zA-Z ]+$", message = "El genero no es valido")
    @NotBlank(message = "El genero es requerido")
    private String gender;

    private String age;

    private String coins;
}
