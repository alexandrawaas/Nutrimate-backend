package com.example.nutrimatebackend.controllers;

import com.example.nutrimatebackend.dtos.allergen.AllergenDTORequest;
import com.example.nutrimatebackend.dtos.allergen.AllergenDTOResponse;
import com.example.nutrimatebackend.dtos.recipe.RecipeDTOResponse;
import com.example.nutrimatebackend.dtos.user.UserDTORequest;
import com.example.nutrimatebackend.dtos.recipe.RecipeDTORequest;
import com.example.nutrimatebackend.dtos.user.UserDTOResponse;
import java.util.List;
import java.util.Set;

import com.example.nutrimatebackend.services.UserService;
import org.apache.coyote.BadRequestException;
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

    @GetMapping("/user/{userId}/allergens")
    public Set<AllergenDTOResponse> getAllergens(@PathVariable Long userId){
        try
        {
            return userService.getAllergens(userId);
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PatchMapping("/user/{userId}/allergens")
    @ResponseStatus(HttpStatus.CREATED)
    public Set<AllergenDTOResponse> updateAllergens(@PathVariable Long userId, @RequestBody Set<AllergenDTORequest> allergenDTORequests){
        try
        {
            return userService.updateAllergens(userId, allergenDTORequests);
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/user/{userId}/favorite-recipes")
    public List<RecipeDTOResponse> getFavoriteRecipes(@PathVariable Long userId){
        try
        {
            return userService.getRecipes(userId);
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/user/{userId}/favorite-recipes")
    @ResponseStatus(HttpStatus.CREATED)
    public RecipeDTOResponse addFavoriteRecipes(@PathVariable Long userId, @RequestBody RecipeDTORequest recipeDTORequest) throws BadRequestException {
        try
        {
            return userService.addRecipe(userId, recipeDTORequest);
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/user/{userId}/favorite-recipes/{recipeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public RecipeDTOResponse deleteFavoriteRecipes(@PathVariable Long userId, @PathVariable Long recipeId){
        try
        {
            return userService.deleteRecipe(userId, recipeId);
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
