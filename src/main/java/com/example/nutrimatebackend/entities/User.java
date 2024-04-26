package com.example.nutrimatebackend.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    String email;
    String password;

    @OneToOne
    Fridge fridge;

    @OneToMany
    List<Allergen> allergens;

    @OneToMany
    List<Recipe> favouriteRecipes;
}
