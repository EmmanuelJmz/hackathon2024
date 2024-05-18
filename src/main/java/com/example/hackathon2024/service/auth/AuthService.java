package com.example.hackathon2024.service.auth;

import com.example.hackathon2024.model.role.Role;
import com.example.hackathon2024.model.role.RoleRepository;
import com.example.hackathon2024.model.user.*;
import com.example.hackathon2024.security.jwt.JwtProvider;
import com.example.hackathon2024.security.service.UserDetailsServiceImpl;
import com.example.hackathon2024.service.user.UserService;
import com.example.hackathon2024.utils.ApiResponse;
import com.example.hackathon2024.utils.email;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager manager;
    private final JwtProvider provider;
    private final PasswordEncoder passwordEncoder;
    private final UserService service;
    private final UserDetailsServiceImpl userDetailsService;
    private final email email;

    @Value("${client.name}")
    private String userName;

    private User user(UserDto user, Role role, PasswordEncoder passwordEncoder, UserRepository userRepository) throws MessagingException, IOException{
        User newUser = new User();
        String id = UUID.randomUUID().toString();
        newUser.setId(id);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRole(role);
        newUser.setBlocked(true);
        if (role.getName().equals("Client")){
            String filePath = "src/main/resources/welcome.html";
            String htmlContent = new String(Files.readAllBytes(Paths.get(filePath)));
            email.sendSimpleMessage(user.getEmail(), "Bienvenido :)", htmlContent);
        } else {
            newUser.setStatus(true);
        }
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

        User saveUser = user(user, role, passwordEncoder, userRepository);
        return new ApiResponse<>(
                saveUser, false, HttpStatus.OK, "Cliente registrado correctamente"
        );
    }

    @Transactional(readOnly = true)
    public ApiResponse<SignedDto> signIn(SignInDto user) {
        try {
            Optional<User> foundUser = service.findByEmail(user.getEmail());
            if (foundUser.isEmpty())
                return new ApiResponse<>(
                        null, true, HttpStatus.NOT_FOUND, "email no registrado"
                );
            User userFound = foundUser.get();
            if (Boolean.FALSE.equals(userFound.getStatus()))
                return new ApiResponse<>(
                        null, true, HttpStatus.UNAUTHORIZED, "Usuario inactivo"
                );
            if (Boolean.FALSE.equals(userFound.getBlocked()))
                return new ApiResponse<>(
                        null, true, HttpStatus.UNAUTHORIZED, "Usuario bloqueado"
                );
            Authentication auth = manager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
            String token = provider.generateToken(userDetails);
            SignedDto signedDto = new SignedDto(token, userFound);
            return new ApiResponse<>(
                    signedDto, false, HttpStatus.OK, "Logueado correctamente!"
            );
        } catch (Exception e) {
            return new ApiResponse<>(
                    null, true, HttpStatus.BAD_REQUEST, "Usuario o contraseña incorrectos"
            );
        }
    }
}
