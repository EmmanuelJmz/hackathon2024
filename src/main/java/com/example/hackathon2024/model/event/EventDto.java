package com.example.hackathon2024.model.event;

import com.example.hackathon2024.model.medal.Medal;
import lombok.Data;

import java.util.Date;

@Data
public class EventDto {
    private String title;
    private String description;
    private Date date;
    private String address;
    private String userId;
    private Medal medal;
}
