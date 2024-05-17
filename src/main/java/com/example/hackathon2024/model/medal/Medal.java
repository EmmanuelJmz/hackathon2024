package com.example.hackathon2024.model.medal;

import com.example.hackathon2024.model.event.Event;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @OneToOne(mappedBy = "medal", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Event event;
}
