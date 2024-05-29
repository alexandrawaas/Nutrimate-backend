package com.example.nutrimatebackend.services;

import com.example.nutrimatebackend.dtos.api.edamam.EdamamResponse;
import com.example.nutrimatebackend.dtos.api.edamam.Hit;
import com.example.nutrimatebackend.dtos.recipe.RecipeDTOResponse;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeService
{
    private final WebClient webClient;

    public RecipeService(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<RecipeDTOResponse> searchRecipes() {
        // TODO: fetch recipes suitable for the users fridges content

        String url = new URIBuilder()
                .setScheme("https")
                .setHost("api.edamam.com")
                .setPath("/api/recipes/v2")
                .addParameter("type", "public")
                .addParameter("app_id", "c0f268ea")
                .addParameter("app_key", "ae91ed6e3daff98e50ae16324d419b63")

                // Search recipes with "chicken"
                .addParameter("q", "Nutella")

                .toString();

        EdamamResponse response = webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(EdamamResponse.class)
                .block();

        List<RecipeDTOResponse> recipeURLs = new ArrayList<>();

        if (response == null) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Recipe not found");
        }

        // TODO: create a converter here
        for (Hit hit : response.getHits()) {
            String recipeURL = hit.getRecipe().getUri();
            RecipeDTOResponse recipeDTOResponse = new RecipeDTOResponse(recipeURL);
            recipeURLs.add(recipeDTOResponse);
        }

        // TODO: add URL converter to fix broken urls
        return recipeURLs;
    }
}
