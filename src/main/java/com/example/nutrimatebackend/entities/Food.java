package com.example.nutrimatebackend.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
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
    Timestamp expireDate;

    @OneToMany
    List<Allergen> allergens;

    // when the food gets opened, save the days to consume this food
    boolean isOpen;
    int daysToConsume;

    // nutritional values
    int calories;
    int fats;
    int saturatedFats;
    int carbs;
    int sugar;
    int fibers;
    int proteins;
    int salt;
}
