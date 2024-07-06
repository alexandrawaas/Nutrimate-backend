package com.example.nutrimatebackend.controllers;

import com.example.nutrimatebackend.dtos.allergen.AllergenDTORequest;
import com.example.nutrimatebackend.dtos.allergen.AllergenDTOResponse;
import com.example.nutrimatebackend.dtos.recipe.FavouriteRecipeDTOResponse;
import com.example.nutrimatebackend.dtos.recipe.RecipeDTORequest;
import com.example.nutrimatebackend.dtos.user.UserDTOResponse;
import com.example.nutrimatebackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

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
    public UserDTOResponse createUser() {
        return userService.createUser();
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

    @PutMapping("/user/allergens")
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
    public List<FavouriteRecipeDTOResponse> getFavoriteRecipes(@RequestParam(required = false) String q){
        return userService.getRecipes(q);
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

    @DeleteMapping("/user")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(){
        userService.deleteUser();
    }
}
