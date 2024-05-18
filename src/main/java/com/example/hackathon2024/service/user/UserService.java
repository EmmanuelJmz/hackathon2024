package com.example.hackathon2024.service.user;

import com.example.hackathon2024.model.role.Role;
import com.example.hackathon2024.model.role.RoleRepository;
import com.example.hackathon2024.model.user.User;
import com.example.hackathon2024.model.user.UserDto;
import com.example.hackathon2024.model.user.UserRepository;
import com.example.hackathon2024.utils.ApiResponse;
import jakarta.mail.MessagingException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Value("${client.name}")
    private String userName;

    private User user(UserDto user, Role role, UserRepository userRepository) throws MessagingException, IOException {
        User newUser = new User();
        String id = UUID.randomUUID().toString();
        newUser.setId(id);
        user.setPassword(user.getPassword());
        newUser.setRole(role);
        newUser.setBlocked(true);
        newUser.setStatus(true);
        BeanUtils.copyProperties(user, newUser);
        return userRepository.save(newUser);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ApiResponse<User> createUser(UserDto user) throws MessagingException, IOException {
        Role role = roleRepository.findByName(userName).orElse(null);
        if (role == null)
            return new ApiResponse<>(
                    null, true, HttpStatus.BAD_REQUEST, "No existe el rol"
            );
        User foundUser = userRepository.findByEmail(user.getEmail()).orElse(null);
        if (foundUser != null)
            return new ApiResponse<>(
                    null, true, HttpStatus.BAD_REQUEST, "Usuario ya registrado"
            );

        User saveUser = user(user, role,userRepository);
        return new ApiResponse<>(
                saveUser, false, HttpStatus.OK, "Cliente registrado correctamente"
        );
    }
}
