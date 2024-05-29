package com.example.nutrimatebackend.dtos.api.openFoodFacts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Product {
    @JsonProperty("categories_tags")
    private String[] categoriesTags;

    @JsonProperty("allergens_tags")
    private String[] allergensTags;

    private Nutriments nutriments;
}
