package com.example.nutrimatebackend.controllers;

import com.example.nutrimatebackend.dtos.allergen.AllergenDTORequest;
import com.example.nutrimatebackend.dtos.allergen.AllergenDTOResponse;
import com.example.nutrimatebackend.dtos.recipe.FavouriteRecipeDTOResponse;
import com.example.nutrimatebackend.dtos.recipe.RecipeDTOResponse;
import com.example.nutrimatebackend.dtos.user.UserDTORequest;
import com.example.nutrimatebackend.dtos.recipe.RecipeDTORequest;
import com.example.nutrimatebackend.dtos.user.UserDTOResponse;
import java.util.List;
import java.util.Set;

import com.example.nutrimatebackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Validated
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTOResponse createUser(@RequestBody UserDTORequest userDTORequest) {
        return userService.add(userDTORequest);
    }

    @GetMapping("/user/allergens")
    public Set<AllergenDTOResponse> getAllergens(){
        try
        {
            return userService.getAllergens();
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PatchMapping("/user/allergens")
    @ResponseStatus(HttpStatus.CREATED)
    public Set<AllergenDTOResponse> updateAllergens(@RequestBody Set<AllergenDTORequest> allergenDTORequests){
        try
        {
            return userService.updateAllergens(allergenDTORequests);
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/user/favourite-recipes")
    public List<FavouriteRecipeDTOResponse> getFavoriteRecipes(){
        try
        {
            return userService.getRecipes();
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/user/favourite-recipes")
    @ResponseStatus(HttpStatus.CREATED)
    public FavouriteRecipeDTOResponse addFavoriteRecipes(@RequestBody RecipeDTORequest recipeDTORequest) {


            return userService.addRecipe(recipeDTORequest);


    }

    @DeleteMapping("/user/favourite-recipes/{recipeId}")
    public FavouriteRecipeDTOResponse deleteFavoriteRecipes(@PathVariable Long recipeId){
        try
        {
            return userService.deleteRecipe(recipeId);
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
