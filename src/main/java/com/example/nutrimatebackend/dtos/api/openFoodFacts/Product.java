package com.example.nutrimatebackend.dtos.api.openFoodFacts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Product {
    @JsonProperty("categories_tags")
    private List<String> categoriesTags;

    @JsonProperty("allergens_tags")
    private List<String> allergensTags;

    @JsonProperty("product_name")
    private String productName;

    private Nutriments nutriments;
}
