package com.example.nutrimatebackend.dtos.recipe;

import com.example.nutrimatebackend.entities.Recipe;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeConverter
{
    public RecipeDTOResponse convertToDTOResponse(Recipe recipe){
        return new RecipeDTOResponse(recipe.getId(), recipe.getUrl());
    }

    public List<RecipeDTOResponse> convertListToDTOResponse(List<Recipe> recipes){
        return recipes.stream().map(recipe -> convertToDTOResponse(recipe)).toList();
    }
}
