package com.example.nutrimatebackend.services;

import com.example.nutrimatebackend.dtos.api.edamam.EdamamResponse;
import com.example.nutrimatebackend.dtos.recipe.RecipeConverter;
import com.example.nutrimatebackend.dtos.recipe.RecipeDTOResponse;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class RecipeService
{
    private final RecipeConverter recipeConverter;
    private final WebClient webClient;

    public RecipeService(WebClient webClient) {
        this.webClient = webClient;
        this.recipeConverter = new RecipeConverter();
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

                // Search term
                .addParameter("q", "Spongebob")

                .toString();

        EdamamResponse response = webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(EdamamResponse.class)
                .block();

        return recipeConverter.convertResponseToDTOList(response);
    }
}
