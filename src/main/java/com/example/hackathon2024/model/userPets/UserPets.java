package com.example.hackathon2024.model.userPets;

import com.example.hackathon2024.model.pet.Pet;
import com.example.hackathon2024.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "user_pets")
@AllArgsConstructor
@NoArgsConstructor
public class UserPets {
    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;
}
