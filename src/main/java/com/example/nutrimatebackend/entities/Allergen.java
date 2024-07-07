package com.example.nutrimatebackend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Allergen {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    String name;

    public Allergen(String name) {
        this.name = name;
    }

    public Allergen() {

    }
}
