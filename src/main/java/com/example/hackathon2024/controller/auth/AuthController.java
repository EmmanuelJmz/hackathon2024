package com.example.hackathon2024.controller.auth;

import com.example.hackathon2024.model.user.SignInDto;
import com.example.hackathon2024.model.user.SignedDto;
import com.example.hackathon2024.model.user.User;
import com.example.hackathon2024.model.user.UserDto;
import com.example.hackathon2024.service.auth.AuthService;
import com.example.hackathon2024.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-greenpal/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/createClient")
    public ResponseEntity<ApiResponse<User>> createClient (@Valid @RequestBody UserDto user){
        try {
            ApiResponse<User> response = authService.createUser(user);
            HttpStatus statusCode = response.isError() ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
            return new ResponseEntity<>(
                    response,
                    statusCode
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            null, true, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PostMapping("/signIn")
    public ResponseEntity<ApiResponse<SignedDto>> signIn (@Valid @RequestBody SignInDto user){
        try {
            ApiResponse<SignedDto> response = authService.signIn(user);
            HttpStatus statusCode = response.isError() ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
            return new ResponseEntity<>(
                    response,
                    statusCode
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            null, true, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
