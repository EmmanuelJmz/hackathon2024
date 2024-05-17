package com.example.hackathon2024.service.user;

import com.example.hackathon2024.model.user.User;
import com.example.hackathon2024.model.user.UserRepository;
import com.example.hackathon2024.utils.ApiResponse;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;


    @Transactional(readOnly = true)
    public ApiResponse<List<User>> getAll () {
        List<User> users = repository.findAll();
        if (users.isEmpty()) {
            return new ApiResponse<>(
                    users, false , HttpStatus.BAD_REQUEST, "No hay usuarios registrados"
            );
        } else {
            return new ApiResponse<>(
                    users, false, HttpStatus.OK, "Usuarios encontrados"
            );
        }
    }

    @Transactional(readOnly = true)
    public ApiResponse<User> findById (String id) {
        User user = repository.findById(id).orElse(null);
        if (user != null) {
            return new ApiResponse<>(
                    user, false, HttpStatus.OK, "Usuario encontrado"
            );
        } else {
            return new ApiResponse<>(
                    null, true, HttpStatus.BAD_REQUEST, "Usuario no encontrado"
            );
        }
    }

    @Transactional(readOnly = true)
    public Optional<User> findByEmail (String email) {
        return repository.findByEmail(email);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ApiResponse<User> deleteById(String id) {
        User user = repository.findById(id).orElse(null);
        if (user != null) {
            repository.deleteById(id);
            return new ApiResponse<>(
                    user, false, HttpStatus.OK, "Usuario eliminado"
            );
        } else {
            return new ApiResponse<>(
                    null, true, HttpStatus.BAD_REQUEST, "Usuario no encontrado"
            );
        }
    }

}
