package com.example.nutrimatebackend.services;

import com.example.nutrimatebackend.dtos.api.edamam.EdamamResponse;
import com.example.nutrimatebackend.dtos.recipe.RecipeConverter;
import com.example.nutrimatebackend.dtos.recipe.RecipeDTOResponse;
import com.example.nutrimatebackend.dtos.recipe.RecipeSearchDTORequest;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

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

    public List<RecipeDTOResponse> searchRecipes(RecipeSearchDTORequest request) {
        // TODO: fetch recipes suitable for the users fridges content

        StringBuilder queries = new StringBuilder();
        for (String category : request.categories) {
            queries.append(category).append(" ");
        }

        String url = new URIBuilder()
                .setScheme("https")
                .setHost("api.edamam.com")
                .setPath("/api/recipes/v2")
                .addParameter("type", "public")
                .addParameter("app_id", env.getProperty("edamam.app.id"))
                .addParameter("app_key", env.getProperty("edamam.app.key"))

                // Search terms
                .addParameter("q", queries.toString())

                .toString();

        EdamamResponse response = webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(EdamamResponse.class)
                .block();

        if (response == null) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Recipe not found");
        }

        return recipeConverter.convertResponseToDTOList(response);
    }
}
