package com.example.hackathon2024.model.event;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, String> {
    List<Event> findByUserId(String userId);
    Optional<Event> findByCode(String code);
}
