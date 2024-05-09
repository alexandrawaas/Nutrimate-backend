package com.example.nutrimatebackend.dtos;

import lombok.Data;

@Data
public class FetchedRecipeDTO {
    String url;

    public FetchedRecipeDTO(String url) {
        this.url = url;
    }
}
