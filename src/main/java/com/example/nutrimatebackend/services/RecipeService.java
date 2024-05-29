package com.example.nutrimatebackend.services;

import com.example.nutrimatebackend.dtos.api.edamam.EdamamResponse;
import com.example.nutrimatebackend.dtos.recipe.RecipeConverter;
import com.example.nutrimatebackend.dtos.recipe.RecipeDTOResponse;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class RecipeService
{
    private final RecipeConverter recipeConverter;
    private final WebClient webClient;
    private final Environment env;

    public RecipeService(WebClient webClient, RecipeConverter recipeConverter, Environment env) {
        this.webClient = webClient;
        this.recipeConverter = recipeConverter;
        this.env = env;
    }

    public List<RecipeDTOResponse> searchRecipes() {
        // TODO: fetch recipes suitable for the users fridges content

        String url = new URIBuilder()
                .setScheme("https")
                .setHost("api.edamam.com")
                .setPath("/api/recipes/v2")
                .addParameter("type", "public")
                .addParameter("app_id", env.getProperty("edamam.app.id"))
                .addParameter("app_key", env.getProperty("edamam.app.key"))

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
