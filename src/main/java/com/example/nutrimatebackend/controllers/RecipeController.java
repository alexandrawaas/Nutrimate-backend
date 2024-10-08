package com.example.nutrimatebackend.controllers;

import com.example.nutrimatebackend.dtos.recipe.RecipeDTOResponse;
import com.example.nutrimatebackend.services.RecipeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipes")
    public List<RecipeDTOResponse> getRecipes(@RequestParam String[] categories, @RequestParam String[] allergens) {
        return recipeService.searchRecipes(categories, allergens);
    }
}
