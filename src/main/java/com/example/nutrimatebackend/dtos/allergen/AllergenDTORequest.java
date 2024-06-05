package com.example.nutrimatebackend.dtos.allergen;

public class AllergenDTORequest
{
    public Long id;
    public String name;

    public AllergenDTORequest(Long id, String name){
        this.id = id;
        this.name = name;
    }
}
