package com.example.nutrimatebackend.dtos.recipe;

import lombok.Data;

@Data
public class RecipeDTOResponse
{
    String url;
    String name;


    public RecipeDTOResponse(String url, String name) {
        this.url = url;
        this.name = name;
    }
}
