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
import com.example.nutrimatebackend.repositories.FridgeRepository;
import com.example.nutrimatebackend.repositories.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService
{
    private final UserRepository userRepository;
    private final FridgeRepository fridgeRepository;
    private final AllergenRepository allergenRepository;

    private final UserConverter userConverter;
    private final AllergenConverter allergenConverter;
    private final RecipeConverter recipeConverter;

    public UserService(
            UserRepository userRepository,
            FridgeRepository fridgeRepository,
            AllergenRepository allergenRepository,
            UserConverter userConverter,
            AllergenConverter allergenConverter,
            RecipeConverter recipeConverter
    ) {
        this.userRepository = userRepository;
        this.fridgeRepository = fridgeRepository;
        this.allergenRepository = allergenRepository;
        this.userConverter = userConverter;
        this.allergenConverter = allergenConverter;
        this.recipeConverter = recipeConverter;
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

    public RecipeDTOResponse addRecipe(Long userId, RecipeDTORequest recipeDTORequest) throws BadRequestException {
        User user = userRepository.findById(userId).orElseThrow();

        List<Recipe> favoriteRecipes = user.getFavouriteRecipes();
        Recipe newRecipe = new Recipe(recipeDTORequest.url);

        if (!favoriteRecipes.contains(newRecipe)) {
            favoriteRecipes.add(newRecipe);

            user.setFavouriteRecipes(favoriteRecipes);
            userRepository.saveAndFlush(user);

            return recipeConverter.convertToDTOResponse(newRecipe);
        }

        throw new BadRequestException("Recipe is already favorite");
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
}
