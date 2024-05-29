package com.example.nutrimatebackend.dtos.api.openFoodFacts;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class Product {
    @JsonProperty("categories_tags")
    private String[] categoriesTags;

    @JsonProperty("allergens_tags")
    private String[] allergensTags;

    Map<String, Object> other = new LinkedHashMap<>();

    @JsonAnySetter
    void setDetail(String key, Object value) {
        other.put(key, value);
    }
}
