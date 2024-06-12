package com.example.nutrimatebackend.dtos.allergen;

import lombok.Getter;

public class AllergenDTOResponse
{
    public Long id;
    @Getter
    public String name;

    public AllergenDTOResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

