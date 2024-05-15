package com.example.nutrimatebackend.dtos.allergen;

import com.example.nutrimatebackend.entities.Allergen;
import org.springframework.stereotype.Service;

@Service
public class AllergenConverter
{
    public AllergenDTOResponse convertToDTOResponse(Allergen allergen) {
        return new AllergenDTOResponse(allergen.getId(), allergen.getName());
    }
}
