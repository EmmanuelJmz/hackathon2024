package com.example.hackathon2024.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse <T>{
    private T data;

    private boolean error;

    private HttpStatus status;

    String message;
}
