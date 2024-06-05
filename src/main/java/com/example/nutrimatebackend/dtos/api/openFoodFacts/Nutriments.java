package com.example.nutrimatebackend.dtos.api.openFoodFacts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Nutriments {
    @JsonProperty("energy-kcal_100g")
    private float energyKcal100g;

    @JsonProperty("fat_100g")
    private float fat100g;

    @JsonProperty("fiber_100g")
    private float fiber100g;

    @JsonProperty("saturated-fat_100g")
    private float saturatedFat100g;

    @JsonProperty("sugars_100g")
    private float sugars100g;

    @JsonProperty("carbohydrates_100g")
    private float carbohydrates100g;

    @JsonProperty("proteins_100g")
    private float proteins100g;

    @JsonProperty("salt_100g")
    private float salt100g;
}
