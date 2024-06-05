package com.example.nutrimatebackend.dtos.food;

import com.example.nutrimatebackend.entities.Allergen;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FoodDTOResponse {
    private Long id;
    private String barcode;
    private LocalDateTime expireDate;
    String category;
    String name;

    boolean isOpen;
    Integer daysToConsume;

    double calories;
    double fats;
    double saturatedFats;
    double carbs;
    double sugar;
    double fibers;
    double proteins;
    double salt;

    List<Allergen> allergens;

    public FoodDTOResponse(Long id, String barcode, LocalDateTime expireDate, String category, String name, boolean isOpen, Integer daysToConsume, double calories, double fats, double saturatedFats, double carbs, double sugar, double fibers, double proteins, double salt, List<Allergen> allergens) {
        this.id = id;
        this.barcode = barcode;
        this.expireDate = expireDate;
        this.category = category;
        this.name = name;
        this.isOpen = isOpen;
        this.daysToConsume = daysToConsume;
        this.calories = calories;
        this.fats = fats;
        this.saturatedFats = saturatedFats;
        this.carbs = carbs;
        this.sugar = sugar;
        this.fibers = fibers;
        this.proteins = proteins;
        this.salt = salt;
        this.allergens = allergens;
    }

    public Long getId() {
        return id;
    }

}
