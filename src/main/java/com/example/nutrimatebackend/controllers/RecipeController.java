package com.example.nutrimatebackend.controllers;

import com.example.nutrimatebackend.dtos.recipe.RecipeDTOResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RecipeController {
    @GetMapping("/recipes")
    public List<RecipeDTOResponse> getRecipes() {
        // TODO: fetch recipes suitable for the users fridges content

        List<RecipeDTOResponse> dummyRecipes = new ArrayList<>();

        dummyRecipes.add(new RecipeDTOResponse("https://www.edamam.com/results/recipe/?recipe=coke-to-float-your-boat-f9d74107de917f6c36431c2fa079670a"));
        dummyRecipes.add(new RecipeDTOResponse("https://www.edamam.com/results/recipe/?recipe=mcdonald%27s-mcgriddle-73ba319ca84fb238617f035d56718323"));
        dummyRecipes.add(new RecipeDTOResponse("https://www.edamam.com/results/recipe/?recipe=spicy-sausage-pizza-ebdd6b4b4f3c4fb3a231866a25992ee3"));

        return dummyRecipes;
    }
}
