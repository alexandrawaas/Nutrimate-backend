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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
@Validated
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserDTOResponse createUser(@RequestBody UserDTORequest userDTORequest) {
        return userService.add(userDTORequest);
    }

    @GetMapping("/{userId}/allergens")
    public Set<AllergenDTOResponse> getAllergens(@PathVariable Long userId){
       return userService.getAllergens(userId);
    }

    @PatchMapping("/{userId}/allergens")
    public Set<AllergenDTOResponse> updateAllergens(@PathVariable Long userId, @RequestBody Set<AllergenDTORequest> allergenDTORequests){
        return userService.updateAllergens(userId, allergenDTORequests);
    }

    @GetMapping("/{userId}/favorite-recipes")
    public List<RecipeDTOResponse> getFavoriteRecipes(@PathVariable Long userId){
      return userService.getRecipes(userId);
    }

    @PostMapping("/{userId}/favorite-recipes")
    public RecipeDTOResponse addFavoriteRecipes(@PathVariable Long userId, @RequestBody RecipeDTORequest recipeDTORequest) throws BadRequestException {
        return userService.addRecipe(userId, recipeDTORequest);
    }

    @DeleteMapping("/{userId}/favorite-recipes/{recipeId}")
    public RecipeDTOResponse deleteFavoriteRecipes(@PathVariable Long userId, @PathVariable Long recipeId){
        return userService.deleteRecipe(userId, recipeId);

    }
}
