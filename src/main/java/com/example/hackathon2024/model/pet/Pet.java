package com.example.hackathon2024.model.pet;

import com.example.hackathon2024.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "pet")
@AllArgsConstructor
@NoArgsConstructor
public class Pet {
    @Id
    private String id;

}
