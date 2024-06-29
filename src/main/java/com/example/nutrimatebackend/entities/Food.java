package com.example.nutrimatebackend.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Food {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    String name;
    String category;
    String barcode;
    LocalDateTime expireDate;

    @ManyToMany()
    List<Allergen> allergens;

    // when the food gets opened, save the days to consume this food
    boolean isOpen;

    @Nullable
    Integer daysToConsume;

    @Nullable
    LocalDateTime timeOpened;

    // nutritional values
    double calories;
    double fats;
    double saturatedFats;
    double carbs;
    double sugar;
    double fibers;
    double proteins;
    double salt;

    // environmental data
    String ecoscoreGrade;
    int ecoscoreScore;

    String imageUrl;

    public Food(String name, String category, String barcode, LocalDateTime expireDate, List<Allergen> allergens, int calories, int fats, int saturatedFats, int carbs, int sugar, int fibers, int proteins, int salt, String ecoscoreGrade, int ecoscoreScore, String imageUrl) {
        isOpen = false;
        daysToConsume = null;
        timeOpened = null;

        this.name = name;
        this.category = category;
        this.barcode = barcode;
        this.expireDate = expireDate;
        this.allergens = allergens;
        this.calories = calories;
        this.fats = fats;
        this.saturatedFats = saturatedFats;
        this.carbs = carbs;
        this.sugar = sugar;
        this.fibers = fibers;
        this.proteins = proteins;
        this.salt = salt;

        this.ecoscoreGrade = ecoscoreGrade;
        this.ecoscoreScore = ecoscoreScore;
        this.imageUrl = imageUrl;
    }

    public Food() {}
}
