package com.example.nutrimatebackend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Recipe implements Comparable<Recipe> {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    String url;

    public Recipe(String url) {
        this.url = url;
    }

    public Recipe() {}

    @Override
    public int compareTo(Recipe other) {
        return url.compareTo(other.url);
    }
}
