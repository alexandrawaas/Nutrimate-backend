package com.example.nutrimatebackend.dtos.fridge;

import com.example.nutrimatebackend.entities.Food;

import java.util.List;

public class FridgeDTORequest
{
    public List<Food> content;

    public FridgeDTORequest(List<Food> content) {
        this.content = content;
    }
}
