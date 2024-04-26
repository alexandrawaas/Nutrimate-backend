package com.example.nutrimatebackend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Timestamp;

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
