package com.example.nutrimatebackend.entities;

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

    @OneToMany(fetch = FetchType.EAGER)
    List<Allergen> allergens;

    // when the food gets opened, save the days to consume this food
    boolean isOpen;
    Integer daysToConsume;

    // nutritional values
    int calories;
    int fats;
    int saturatedFats;
    int carbs;
    int sugar;
    int fibers;
    int proteins;
    int salt;

    public Food(String name, String category, String barcode, LocalDateTime expireDate, List<Allergen> allergens, int calories, int fats, int saturatedFats, int carbs, int sugar, int fibers, int proteins, int salt) {
        isOpen = false;
        daysToConsume = 0;

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
    }

    public Food() {}

    public int calculateEnvironmentalScore(){
        return 42;
    }
}
