package com.example.hackathon2024.model.event;

import com.example.hackathon2024.model.medal.Medal;
import com.example.hackathon2024.model.user.User;
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

    private String imageUrl;

    private Date date;

    @ElementCollection
    private List<String> participants = new ArrayList<>();

    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "medal_id", referencedColumnName = "id")
    private Medal medal;

}
