package com.example.nutrimatebackend.dtos.allergen;

public class AllergenDTOResponse
{
    public Long id;
    public String name;

    public AllergenDTOResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

