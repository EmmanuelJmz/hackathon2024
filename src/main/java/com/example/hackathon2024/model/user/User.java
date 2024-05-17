package com.example.hackathon2024.model.user;

import com.example.hackathon2024.model.event.Event;
import com.example.hackathon2024.model.role.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;

    @Column(length = 50, nullable = false)
    private String fullName;

    @Column(columnDefinition = "TIMESTAMP DEFAULT NOW()", insertable = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(length = 150, nullable = false)
    @JsonIgnore
    private String password;

    @Column(length = 50, nullable = false)
    private String state;

    @Column(length = 50, nullable = false)
    private String city;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String age;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String coins;

    @Column(columnDefinition = "BOOL DEFAULT FALSE")
    private Boolean status;

    @Column(columnDefinition = "BOOL DEFAULT FALSE")
    private Boolean blocked;

    @ManyToOne
    @JoinColumn(name = "role")
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Event> events;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    @JsonIgnore
    private Event event;
}
