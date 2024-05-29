package com.example.nutrimatebackend.dtos.api.openFoodFacts;

import lombok.Data;

@Data
public class OpenFoodFactsResponse {
    private String code;
    private Product product;
}
