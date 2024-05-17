package com.example.hackathon2024.model.event;

import com.example.hackathon2024.model.eventImages.EventImages;
import com.example.hackathon2024.model.medal.Medal;
import com.example.hackathon2024.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "events")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    private String id;

    private String title;

    private String description;

    @Nullable
    private String imageUrl;

    private Date date;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Nullable
    private List<User> participants;

    private String address;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "medal_id", referencedColumnName = "id")
    private Medal medal;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    @Nullable
    private List<EventImages> images;
}
