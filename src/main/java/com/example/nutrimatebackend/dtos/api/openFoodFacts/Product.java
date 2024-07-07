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

    @JsonProperty("ecoscore_grade")
    private String ecoscoreGrade;

    @JsonProperty("ecoscore_score")
    private int ecoscoreScore;

    private Nutriments nutriments;

    @JsonProperty("selected_images")
    private SelectedImages selectedImages;
}

