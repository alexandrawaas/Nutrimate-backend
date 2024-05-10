package com.example.nutrimatebackend.controllers;

import com.example.nutrimatebackend.dtos.AllergenDTO;
import com.example.nutrimatebackend.dtos.CreateFridgeDTO;
import com.example.nutrimatebackend.dtos.CreateUserDTO;
import com.example.nutrimatebackend.dtos.FavoriteRecipeDTO;
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

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody CreateUserDTO createUserDTO) {
        User newUser = userRepository.saveAndFlush(new User(createUserDTO.eMail, createUserDTO.password, new Fridge(), Set.of(), List.of()));
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
    public ResponseEntity<Set<Allergen>> updateAllergens(@PathVariable Long userId, @RequestBody Set<AllergenDTO> allergenDTOs){
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            Set<Allergen> allergens = user.getAllergens();
            Set<Allergen> newAllergens = allergenDTOs.stream().map(allergenDTO -> allergenRepository.findById(allergenDTO.id).orElse(null)).filter(allergen -> allergen != null).collect(Collectors.toSet());

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
    public ResponseEntity<FavoriteRecipeDTO> addFavoriteRecipes(@PathVariable Long userId, @RequestBody FavoriteRecipeDTO favoriteRecipeDTO){
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            List<Recipe> favoriteRecipes = user.getFavouriteRecipes();

            if (favoriteRecipes.stream().filter(favoriteRecipe -> favoriteRecipe.getUrl().equals(favoriteRecipeDTO.url)).toList().isEmpty()){
                favoriteRecipes.add(new Recipe(favoriteRecipeDTO.url));

                user.setFavouriteRecipes(favoriteRecipes);
                userRepository.saveAndFlush(user);

                return ResponseEntity.status(201).body(favoriteRecipeDTO);
            }

            return ResponseEntity.status(403).build();
        }
        else {
            return ResponseEntity.status(401).build();
        }
    }

    @DeleteMapping("/{userId}/favorite-recipes/{recipeId}")
    public ResponseEntity deleteFavoriteRecipes(@PathVariable Long userId, @PathVariable Long recipeId){

        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            List<Recipe> favoriteRecipes = user.getFavouriteRecipes();

            user.getFavouriteRecipes();
        }

    }
}
