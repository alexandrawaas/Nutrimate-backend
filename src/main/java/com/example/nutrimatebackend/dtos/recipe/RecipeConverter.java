package com.example.nutrimatebackend.dtos.recipe;

import com.example.nutrimatebackend.dtos.api.edamam.EdamamResponse;
import com.example.nutrimatebackend.dtos.api.edamam.Hit;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.http.HttpStatus;
import com.example.nutrimatebackend.entities.Recipe;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeConverter
{
    public FavouriteRecipeDTOResponse convertToFavouriteRecipeDTOResponse(Recipe recipe){
        return new FavouriteRecipeDTOResponse(recipe.getId(), recipe.getUrl(), recipe.getName());
    }

    public List<RecipeDTOResponse> convertResponseToDTOList(EdamamResponse response) {
        List<RecipeDTOResponse> recipeURLs = new ArrayList<>();

        for (Hit hit : response.getHits()) {
            String recipeURL = hit.getRecipe().getUri();
            String recipeName = hit.getRecipe().getLabel();

            // The returned urls from Edamam are broken
            // Let's fix them here
            try {
                String recipeID = new URIBuilder(recipeURL).getFragment();

                String fixedRecipeURL = new URIBuilder()
                        .setScheme("https")
                        .setHost("www.edamam.com")
                        .setPath("/results/recipe")
                        .addParameter("recipe", recipeID)
                        .toString();

                RecipeDTOResponse recipeDTOResponse = new RecipeDTOResponse(fixedRecipeURL, recipeName);
                recipeURLs.add(recipeDTOResponse);

            } catch (URISyntaxException e) {
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Can't repair broken URL from Edamam"
                );
            }
        }
        return recipeURLs;
    }
}
