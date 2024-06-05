package com.example.nutrimatebackend.dtos.recipe;

import lombok.Data;

@Data
public class RecipeDTOResponse
{
    Long id;
    String url;


    public RecipeDTOResponse(Long id, String url) {
        this.id = id;
        this.url = url;
    }
}
