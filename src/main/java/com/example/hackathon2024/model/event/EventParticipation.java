package com.example.hackathon2024.model.event;

import com.example.hackathon2024.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "event_participations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventParticipation {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
