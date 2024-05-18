package com.example.hackathon2024.service.user;

import com.example.hackathon2024.model.user.User;
import com.example.hackathon2024.model.user.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    @Transactional(readOnly = true)
    public Optional<User> findByEmail (String email) {
        return repository.findByEmail(email);
    }

}
