package com.example.nutrimatebackend.dtos.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class OpenFoodFactsResponse {
    String code;
    Nutriments nutriments;
    Product product;

    @Data
    public static class Product {
        @JsonProperty("categories_tags")
        List<String> categoriesTags;

        @JsonProperty("allergens_tags")
        List<String> allergensTags;
    }

    @Data
    public static class Nutriments {
        @JsonProperty("energy-kcal_100g")
        double energyKcal100g;

        @JsonProperty("fat_100g")
        double fat100g;

        @JsonProperty("fiber_100g")
        double fiber100g;

        @JsonProperty("saturated-fat_100g")
        double saturatedFat100g;

        @JsonProperty("sugars_100g")
        double sugars100g;

        @JsonProperty("carbohydrates_100g")
        double carbohydrates100g;

        @JsonProperty("proteins_100g")
        double proteins100g;

        @JsonProperty("salt_100g")
        double salt100g;
    }
}
