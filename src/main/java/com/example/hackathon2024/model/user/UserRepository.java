package com.example.hackathon2024.model.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail (String email);

    List<User> findAllByRoleName(String role);

    Boolean existsByRoleName(String role);
}
