package com.example.nutrimatebackend.controllers;

import com.example.nutrimatebackend.entities.Allergen;
import com.example.nutrimatebackend.repositories.AllergenRepository;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

@RestController
public class AllergenController {
    private final AllergenRepository allergenRepository;

    public AllergenController(AllergenRepository allergenRepository) {
        this.allergenRepository = allergenRepository;
    }

    @GetMapping(value = "/allergens")
    public List<Allergen> getAllAllergens() {
        return allergenRepository.findAll();
    }

}
