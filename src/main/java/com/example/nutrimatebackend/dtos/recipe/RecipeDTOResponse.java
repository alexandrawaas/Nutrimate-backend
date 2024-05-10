package com.example.nutrimatebackend.dtos.recipe;

import lombok.Data;

@Data
public class RecipeDTOResponse
{
    String url;

    public RecipeDTOResponse(String url) {
        this.url = url;
    }
}
