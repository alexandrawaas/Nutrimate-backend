package com.example.nutrimatebackend.controllers;

import com.example.nutrimatebackend.dtos.allergen.AllergenDTOResponse;
import com.example.nutrimatebackend.services.AllergenService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

@RestController
public class AllergenController {
    private final AllergenService allergenService;

    public AllergenController(AllergenService allergenService) {
        this.allergenService = allergenService;
    }

    @GetMapping(value = "/allergens")
    public ResponseEntity<List<AllergenDTOResponse>> getAllAllergens() {

        List<AllergenDTOResponse> allergens = allergenService.getAllAllergens();
        return ResponseEntity.ok()
                .header("Cache-Control", "max-age=86400") // 86400 = 1 Tag
                .body(allergens);
    }

}
