package com.example.nutrimatebackend.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import java.util.List;
import java.util.Set;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    String email;

    @OneToOne() @Cascade(org.hibernate.annotations.CascadeType.ALL)
    Fridge fridge;


    @ManyToMany()
    Set<Allergen> allergens;

    @OneToMany()
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    List<Recipe> favouriteRecipes;

    public User(String email, Fridge fridge, Set<Allergen> allergens, List<Recipe> favouriteRecipes) {
        this.email = email;
        this.fridge = fridge;
        this.allergens = allergens;
        this.favouriteRecipes = favouriteRecipes;
    }

    public User() {

    }
}
