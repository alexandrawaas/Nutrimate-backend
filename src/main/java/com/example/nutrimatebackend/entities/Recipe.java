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
    String name;

    public Recipe(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public Recipe() {}

    @Override
    public int compareTo(Recipe other) {
        return url.compareTo(other.url);
    }
}
