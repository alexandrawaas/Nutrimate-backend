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

    public User(String email, String password, Fridge fridge, List<Allergen> allergens, List<Recipe> favouriteRecipes) {
        this.email = email;
        this.password = password;
        this.fridge = fridge;
        this.allergens = allergens;
        this.favouriteRecipes = favouriteRecipes;
    }

    public User() {

    }
}
