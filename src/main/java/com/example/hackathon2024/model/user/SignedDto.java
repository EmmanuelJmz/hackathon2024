package com.example.hackathon2024.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class SignedDto {
    String token;
    User user;
}