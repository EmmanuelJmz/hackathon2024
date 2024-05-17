package com.example.hackathon2024.model.medal;

import com.example.hackathon2024.model.event.Event;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "medal")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medal {
    @Id
    private String id;

    private String name;
    private String imageUrl;

    @OneToOne(mappedBy = "medal", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Event event;
}
