package com.example.hackathon2024.controller.auth;

import com.example.hackathon2024.model.user.User;
import com.example.hackathon2024.model.user.UserDto;
import com.example.hackathon2024.service.user.UserService;
import com.example.hackathon2024.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api-greenpal/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/createClient")
    public ResponseEntity<ApiResponse<User>> createClient (@Valid @RequestBody UserDto user){
        try {
            ApiResponse<User> response = userService.createUser(user);
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
