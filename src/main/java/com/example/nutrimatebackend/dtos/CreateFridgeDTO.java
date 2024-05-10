package com.example.nutrimatebackend.dtos;

import com.example.nutrimatebackend.entities.Food;

import java.util.List;

public class CreateFridgeDTO {
    public List<Food> content;

    public CreateFridgeDTO(List<Food> content) {
        this.content = content;
    }
}
