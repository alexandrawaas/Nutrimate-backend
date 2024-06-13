package com.example.nutrimatebackend.services;

import com.example.nutrimatebackend.dtos.allergen.AllergenConverter;
import com.example.nutrimatebackend.dtos.allergen.AllergenDTORequest;
import com.example.nutrimatebackend.dtos.allergen.AllergenDTOResponse;
import com.example.nutrimatebackend.dtos.recipe.FavouriteRecipeDTOResponse;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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

    public User saveUser(User user){
        return userRepository.saveAndFlush(user);
    }

    public UserDTOResponse add(UserDTORequest userDTORequest){
        User newUser = userRepository.saveAndFlush(new User(userDTORequest.eMail, userDTORequest.password, new Fridge(), Set.of(), List.of()));

        return userConverter.convertToUserDTOResponse(newUser);
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

    public List<FavouriteRecipeDTOResponse> getRecipes(){
        User user = getCurrentUser();
        return user.getFavouriteRecipes().stream().map(recipeConverter::convertToFavouriteRecipeDTOResponse).toList();
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

    public String getEmail() {
        OAuth2AuthenticationToken authentication = (OAuth2AuthenticationToken) SecurityContextHolder
                .getContext()
                .getAuthentication();

        return authentication.getPrincipal().getAttribute("email");
    }
}
