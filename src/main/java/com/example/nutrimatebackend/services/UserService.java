package com.example.nutrimatebackend.services;

import com.example.nutrimatebackend.dtos.allergen.AllergenConverter;
import com.example.nutrimatebackend.dtos.allergen.AllergenDTORequest;
import com.example.nutrimatebackend.dtos.allergen.AllergenDTOResponse;
import com.example.nutrimatebackend.dtos.recipe.RecipeConverter;
import com.example.nutrimatebackend.dtos.recipe.RecipeDTORequest;
import com.example.nutrimatebackend.dtos.recipe.RecipeDTOResponse;
import com.example.nutrimatebackend.dtos.user.UserConverter;
import com.example.nutrimatebackend.dtos.user.UserDTORequest;
import com.example.nutrimatebackend.dtos.user.UserDTOResponse;
import com.example.nutrimatebackend.entities.Allergen;
import com.example.nutrimatebackend.entities.Fridge;
import com.example.nutrimatebackend.entities.Recipe;
import com.example.nutrimatebackend.entities.User;
import com.example.nutrimatebackend.repositories.AllergenRepository;
import com.example.nutrimatebackend.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService
{
    private final UserRepository userRepository;
    private final AllergenRepository allergenRepository;

    private final UserConverter userConverter;
    private final AllergenConverter allergenConverter;
    private final RecipeConverter recipeConverter;

    public UserService(
            UserRepository userRepository,
            AllergenRepository allergenRepository,
            UserConverter userConverter,
            AllergenConverter allergenConverter,
            RecipeConverter recipeConverter
    ) {
        this.userRepository = userRepository;
        this.allergenRepository = allergenRepository;
        this.userConverter = userConverter;
        this.allergenConverter = allergenConverter;
        this.recipeConverter = recipeConverter;
    }

    public User getCurrentUser(){
        //TODO: Implement with Auth
        return userRepository.findAll().getFirst();
    }

    public UserDTOResponse add(UserDTORequest userDTORequest){
        User newUser = userRepository.saveAndFlush(new User(userDTORequest.eMail, userDTORequest.password, new Fridge(), Set.of(), List.of()));

        return userConverter.convertToUserDTOResponse(newUser);
    }

    public Set<AllergenDTOResponse> getAllergens(Long userId){
        User user = userRepository.findById(userId).orElseThrow();
        return allergenConverter.convertSetToDTOResponse(user.getAllergens());
    }

    public Set<AllergenDTOResponse> updateAllergens(Long userId, Set<AllergenDTORequest> allergenDTORequest){
        User user = userRepository.findById(userId).orElseThrow();

        Set<Allergen> newAllergens = allergenDTORequest
                .stream()
                .map(allergenDTOResponse -> allergenRepository.findById(allergenDTOResponse.id).orElseThrow())
                .collect(Collectors.toSet());

        user.setAllergens(newAllergens);
        userRepository.saveAndFlush(user);

        return allergenConverter.convertSetToDTOResponse(newAllergens);
    }

    public List<RecipeDTOResponse> getRecipes(Long userId){
        User user = userRepository.findById(userId).orElseThrow();

        return recipeConverter.convertListToDTOResponse(user.getFavouriteRecipes());
    }

    public RecipeDTOResponse addRecipe(Long userId, RecipeDTORequest recipeDTORequest) {
        User user = userRepository.findById(userId).orElseThrow();

        List<Recipe> favoriteRecipes = user.getFavouriteRecipes();
        Recipe newRecipe = new Recipe(recipeDTORequest.url);

        if (!favoriteRecipes.contains(newRecipe)) {
            favoriteRecipes.add(newRecipe);

            user.setFavouriteRecipes(favoriteRecipes);
            userRepository.saveAndFlush(user);

            return recipeConverter.convertToDTOResponse(newRecipe);
        }
        return recipeConverter.convertToDTOResponse(newRecipe);
    }

    public RecipeDTOResponse deleteRecipe(Long userId, Long recipeId){
        User user = userRepository.findById(userId).orElseThrow();

        List<Recipe> favoriteRecipes = user.getFavouriteRecipes();
        Recipe deletedRecipe = favoriteRecipes.stream().filter(recipe -> recipe.getId().equals(recipeId)).findFirst().orElseThrow();

        favoriteRecipes.remove(deletedRecipe);

        user.setFavouriteRecipes(favoriteRecipes);
        userRepository.saveAndFlush(user);

        return recipeConverter.convertToDTOResponse(deletedRecipe);
    }

    public String getEmail() {
        OAuth2AuthenticationToken authentication = (OAuth2AuthenticationToken) SecurityContextHolder
                .getContext()
                .getAuthentication();

        return authentication.getPrincipal().getAttribute("email");
    }
}
