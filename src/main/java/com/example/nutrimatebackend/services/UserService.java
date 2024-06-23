package com.example.nutrimatebackend.services;

import com.example.nutrimatebackend.dtos.allergen.AllergenConverter;
import com.example.nutrimatebackend.dtos.allergen.AllergenDTORequest;
import com.example.nutrimatebackend.dtos.allergen.AllergenDTOResponse;
import com.example.nutrimatebackend.dtos.recipe.FavouriteRecipeDTOResponse;
import com.example.nutrimatebackend.dtos.recipe.RecipeConverter;
import com.example.nutrimatebackend.dtos.recipe.RecipeDTORequest;
import com.example.nutrimatebackend.dtos.user.UserConverter;
import com.example.nutrimatebackend.dtos.user.UserDTOResponse;
import com.example.nutrimatebackend.entities.Allergen;
import com.example.nutrimatebackend.entities.Fridge;
import com.example.nutrimatebackend.entities.Recipe;
import com.example.nutrimatebackend.entities.User;
import com.example.nutrimatebackend.repositories.AllergenRepository;
import com.example.nutrimatebackend.repositories.UserRepository;
import com.example.nutrimatebackend.utils.HttpRequestUtil;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService
{
    private final UserRepository userRepository;
    private final AllergenRepository allergenRepository;

    private final AllergenConverter allergenConverter;
    private final RecipeConverter recipeConverter;
    private final UserConverter userConverter;

    private final HttpRequestUtil httpRequestUtil;

    public UserService(
            UserRepository userRepository,
            AllergenRepository allergenRepository,
            AllergenConverter allergenConverter,
            RecipeConverter recipeConverter,
            UserConverter userConverter,
            HttpRequestUtil httpRequestUtil
    ) {
        this.userRepository = userRepository;
        this.allergenRepository = allergenRepository;
        this.allergenConverter = allergenConverter;
        this.recipeConverter = recipeConverter;
        this.userConverter = userConverter;
        this.httpRequestUtil = httpRequestUtil;
    }

    public User getCurrentUser() {
        return userRepository.findByEmail(
                httpRequestUtil.getUserEmailFromRequest()
        );
    }

    public void saveUser(User user) {
        userRepository.saveAndFlush(user);
    }

    public UserDTOResponse createUser() {
        User user = getCurrentUser();

        // If the user already exists, don't add a new one and return
        if (user != null) {
            return userConverter.convertToUserDTOResponse(user);
        }

        User rawUser = new User(
                httpRequestUtil.getUserEmailFromRequest(),
                new Fridge(),
                Collections.emptySet(),
                Collections.emptyList()
        );

        User createdUser = userRepository.save(rawUser);

        return userConverter.convertToUserDTOResponse(createdUser);
    }

    public Set<AllergenDTOResponse> getAllergens(){
        User user = getCurrentUser();
        return allergenConverter.convertSetToDTOResponse(user.getAllergens());
    }

    public Set<AllergenDTOResponse> updateAllergens(Set<AllergenDTORequest> allergenDTORequest){
        User user = getCurrentUser();

        Set<Allergen> newAllergens = allergenDTORequest
                .stream()
                .map(allergenDTOResponse -> allergenRepository.findById(allergenDTOResponse.id).orElseThrow())
                .collect(Collectors.toSet());

        user.setAllergens(newAllergens);
        userRepository.saveAndFlush(user);

        return allergenConverter.convertSetToDTOResponse(newAllergens);
    }

    public List<FavouriteRecipeDTOResponse> getRecipes(String searchTerm){
        User user = getCurrentUser();
        if(searchTerm == null || searchTerm.isEmpty()){
            return user.getFavouriteRecipes().stream().map(recipeConverter::convertToFavouriteRecipeDTOResponse).toList();
        }
        return user.getFavouriteRecipes().stream().filter(
                recipe -> recipe.getName().toLowerCase().contains(searchTerm.toLowerCase())
        ).map(recipeConverter::convertToFavouriteRecipeDTOResponse).toList();
    }

    public FavouriteRecipeDTOResponse addRecipe(RecipeDTORequest recipeDTORequest) {

        User user = getCurrentUser();
        List<Recipe> favoriteRecipes = user.getFavouriteRecipes();
        Recipe existingRecipe = favoriteRecipes.stream().filter(recipe -> recipe.getUrl().equals(recipeDTORequest.url)).findFirst().orElse(null);

        if (existingRecipe == null) {
            Recipe newRecipe = new Recipe(recipeDTORequest.url, recipeDTORequest.name);
            favoriteRecipes.add(newRecipe);

            user.setFavouriteRecipes(favoriteRecipes);
            userRepository.save(user);

            newRecipe = user.getFavouriteRecipes().stream().filter(recipe -> recipe.getUrl().equals(recipeDTORequest.url)).findFirst().orElseThrow();

            return recipeConverter.convertToFavouriteRecipeDTOResponse(newRecipe);
        }
        return recipeConverter.convertToFavouriteRecipeDTOResponse(existingRecipe);
    }

    public FavouriteRecipeDTOResponse deleteRecipe(Long recipeId){
        User user = getCurrentUser();

        List<Recipe> favoriteRecipes = user.getFavouriteRecipes();
        Recipe deletedRecipe = favoriteRecipes.stream().filter(recipe -> recipe.getId().equals(recipeId)).findFirst().orElseThrow();

        favoriteRecipes.remove(deletedRecipe);

        user.setFavouriteRecipes(favoriteRecipes);
        userRepository.saveAndFlush(user);

        return recipeConverter.convertToFavouriteRecipeDTOResponse(deletedRecipe);
    }
}
