package com.example.hackathon2024.model.eventImages;

import com.example.hackathon2024.model.event.Event;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "event_images")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    @JsonIgnore
    private Event event;

    @Column(name = "image_key")
    private String imageKey;

    @Column(name = "image_url")
    private String imageUrl;
}
