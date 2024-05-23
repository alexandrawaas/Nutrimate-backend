package com.example.nutrimatebackend.services;

import com.example.nutrimatebackend.dtos.api.EdamamRawRecipeResponse;
import com.example.nutrimatebackend.dtos.recipe.RecipeDTOResponse;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Collections;
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
                .addParameter("q", "chicken")

                .toString();

        EdamamRawRecipeResponse response = webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(EdamamRawRecipeResponse.class)
                .block();

        List<RecipeDTOResponse> recipeURLs = new ArrayList<>();

        if (response == null) {
            return Collections.emptyList();
        }

        for (EdamamRawRecipeResponse.Hit hit : response.getHits()) {
            String recipeURL = hit
                    .get_links()
                    .getSelf()
                    .getHref();

            RecipeDTOResponse recipeDTOResponse = new RecipeDTOResponse(recipeURL);
            recipeURLs.add(recipeDTOResponse);
        }

        return recipeURLs;
    }
}
