package com.example.nutrimatebackend.controllers;

import com.example.nutrimatebackend.dtos.allergen.AllergenDTOResponse;
import com.example.nutrimatebackend.dtos.user.UserDTORequest;
import com.example.nutrimatebackend.dtos.recipe.RecipeDTORequest;
import com.example.nutrimatebackend.entities.Allergen;
import com.example.nutrimatebackend.entities.Fridge;
import com.example.nutrimatebackend.entities.Recipe;
import com.example.nutrimatebackend.entities.User;
import com.example.nutrimatebackend.repositories.AllergenRepository;
import com.example.nutrimatebackend.repositories.FridgeRepository;
import com.example.nutrimatebackend.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user") //Auth  Endpunkt für reg,login/out?
@Validated
public class UserController {
    private final UserRepository userRepository;
    private final FridgeRepository fridgeRepository;
    private final AllergenRepository allergenRepository;

    @Autowired
    public UserController(UserRepository userRepository, FridgeRepository fridgeRepository, AllergenRepository allergenRepository) {
        this.userRepository = userRepository;
        this.fridgeRepository = fridgeRepository;
        this.allergenRepository = allergenRepository;
    }

    // TODO: User zurückgeben
    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody UserDTORequest userDTORequest) {
        User newUser = userRepository.saveAndFlush(new User(userDTORequest.eMail, userDTORequest.password, new Fridge(), Set.of(), List.of()));
        return ResponseEntity.status(HttpStatus.CREATED).body("User created");
    }

    @GetMapping("/{userId}/allergens")
    public ResponseEntity<Set<Allergen>> getAllergens(@PathVariable Long userId){
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get().getAllergens());
        }
        else {
            return ResponseEntity.status(401).build();
        }
    }

    @PatchMapping("/{userId}/allergens")
    public ResponseEntity<Set<Allergen>> updateAllergens(@PathVariable Long userId, @RequestBody Set<AllergenDTOResponse> allergenDTOResponses){
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            Set<Allergen> allergens = user.getAllergens();
            Set<Allergen> newAllergens = allergenDTOResponses.stream().map(allergenDTOResponse -> allergenRepository.findById(allergenDTOResponse.id).orElse(null)).filter(allergen -> allergen != null).collect(Collectors.toSet());

            allergens.addAll(newAllergens);

            user.setAllergens(allergens);
            userRepository.saveAndFlush(user);

            return ResponseEntity.ok(allergens);
        }
        else {
            return ResponseEntity.status(401).build();
        }
    }

    @GetMapping("/{userId}/favorite-recipes")
    public ResponseEntity<List<Recipe>> getFavoriteRecipes(@PathVariable Long userId){
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get().getFavouriteRecipes());
        }
        else {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/{userId}/favorite-recipes")
    public ResponseEntity<RecipeDTORequest> addFavoriteRecipes(@PathVariable Long userId, @RequestBody RecipeDTORequest recipeDTORequest){
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            List<Recipe> favoriteRecipes = user.getFavouriteRecipes();

            if (favoriteRecipes.stream().filter(favoriteRecipe -> favoriteRecipe.getUrl().equals(recipeDTORequest.url)).toList().isEmpty()){
                favoriteRecipes.add(new Recipe(recipeDTORequest.url));

                user.setFavouriteRecipes(favoriteRecipes);
                userRepository.saveAndFlush(user);

                return ResponseEntity.status(201).body(recipeDTORequest);
            }

            return ResponseEntity.status(403).build();
        }
        else {
            return ResponseEntity.status(401).build();
        }
    }

    // TODO: return the deleted recipe
    @DeleteMapping("/{userId}/favorite-recipes/{recipeId}")
    public void deleteFavoriteRecipes(@PathVariable Long userId, @PathVariable Long recipeId){

        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            List<Recipe> favoriteRecipes = user.getFavouriteRecipes();

            user.getFavouriteRecipes();
        }


    }
}
