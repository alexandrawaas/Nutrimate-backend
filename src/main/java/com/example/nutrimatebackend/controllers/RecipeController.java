package com.example.nutrimatebackend.controllers;

import com.example.nutrimatebackend.dtos.api.EdamamRawRecipeResponse;
import com.example.nutrimatebackend.dtos.recipe.RecipeDTOResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RecipeController {
    @GetMapping("/recipes")
    public List<RecipeDTOResponse> getRecipes() {
        // TODO: fetch recipes suitable for the users fridges content

        RestTemplate restTemplate = new RestTemplate();

        String url = "https://api.edamam.com/api/recipes/v2?type=public&q=chicken&app_id=c0f268ea&app_key=ae91ed6e3daff98e50ae16324d419b63";

        EdamamRawRecipeResponse response = restTemplate.getForObject(url, EdamamRawRecipeResponse.class);

        List<RecipeDTOResponse> recipeURLs = new ArrayList<>();

        for (EdamamRawRecipeResponse.Hit hit : response.getHits()) {
            recipeURLs.add(new RecipeDTOResponse(
                    hit
                            .get_links()
                            .getSelf()
                            .getHref())
            );
        }

        return recipeURLs;
    }
}
