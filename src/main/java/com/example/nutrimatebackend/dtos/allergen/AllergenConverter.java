package com.example.nutrimatebackend.dtos.allergen;

import com.example.nutrimatebackend.entities.Allergen;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AllergenConverter
{
    public AllergenDTOResponse convertToDTOResponse(Allergen allergen) {
        return new AllergenDTOResponse(allergen.getId(), allergen.getName());
    }

    public Set<AllergenDTOResponse> convertSetToDTOResponse(Set<Allergen> allergens) {
        return allergens.stream().map(this::convertToDTOResponse).collect(Collectors.toSet());
    }
}
