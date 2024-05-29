package com.example.nutrimatebackend.dtos.api;

import lombok.Data;

import java.util.List;

@Data
public class OpenFoodFactsResponse {
    String code;
    Nutriments nutriments;
    Product product;

    @Data
    public static class Product {
        List<String> categories_tags;
        List<String> allergens_tags;
    }

    @Data
    public static class Nutriments {
        double energyKcal_100g;
        double fat_100g;
        double fiber_100g;
        double saturatedFat_100g;
        double sugars_100g;
        double carbohydrates_100g;
        double proteins_100g;
        double salt_100g;
    }
}
