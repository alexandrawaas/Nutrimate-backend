package com.example.nutrimatebackend.services;

import com.example.nutrimatebackend.dtos.allergen.AllergenDTOResponse;
import com.example.nutrimatebackend.entities.Food;
import com.example.nutrimatebackend.repositories.AllergenRepository;
import com.example.nutrimatebackend.dtos.allergen.AllergenConverter;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AllergenService
{
    private final AllergenRepository allergenRepository;
    private final AllergenConverter allergenConverter;

    public AllergenService(AllergenRepository allergenRepository, AllergenConverter allergenConverter) {
        this.allergenRepository = allergenRepository;
        this.allergenConverter = allergenConverter;
    }

    public List<AllergenDTOResponse> getAllAllergens() {
        return allergenRepository.findAll().stream().map(allergenConverter::convertToDTOResponse).toList();
    }


}

