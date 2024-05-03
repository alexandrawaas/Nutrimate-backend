package com.example.nutrimatebackend.utils;

import com.example.nutrimatebackend.entities.Allergen;
import com.example.nutrimatebackend.entities.Fridge;
import com.example.nutrimatebackend.entities.Recipe;
import com.example.nutrimatebackend.entities.User;
import com.example.nutrimatebackend.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
class Seeder {

    private boolean enableSeeder = true;
    // TODO: Drop Database if enableSeeder is true

    private static final Logger log = LoggerFactory.getLogger(Seeder.class);

    @Bean
    CommandLineRunner initDatabase(
            AllergenRepository allergenRepository,
            UserRepository userRepository,
            FoodRepository foodRepository,
            FridgeRepository fridgeRepository,
            RecipeRepository recipeRepository
    ) {
        if(enableSeeder) {
            return args -> {

                // Seed categories
                log.info("Preloading " + allergenRepository.save(new Allergen("Lactose")));
                log.info("Preloading " + allergenRepository.save(new Allergen("Peanuts")));
                log.info("Preloading " + allergenRepository.save(new Allergen("Gluten")));
                log.info("Preloading " + allergenRepository.save(new Allergen("Shellfish")));
                log.info("Preloading " + allergenRepository.save(new Allergen("Soy")));
                log.info("Preloading " + allergenRepository.save(new Allergen("Tree nuts")));
                log.info("Preloading " + allergenRepository.save(new Allergen("Eggs")));
                log.info("Preloading " + allergenRepository.save(new Allergen("Fish")));
                log.info("Preloading " + allergenRepository.save(new Allergen("Wheat")));
                log.info("Preloading " + allergenRepository.save(new Allergen("Sesame")));

                List myFood = foodRepository.findAll();

                Fridge myFridge = new Fridge(myFood);

                log.info("Preloading " + fridgeRepository.save(myFridge));

                myFridge = fridgeRepository.findById(1L).get();

                List<Allergen> someAllergenes = allergenRepository.findAll().subList(0, 3);

                List<Recipe> someRecipes = recipeRepository.findAll();

                log.info("Preloading " + userRepository.save(new User(
                        "dummyuser@email.com",
                        "securePassword123",
                        myFridge,
                        someAllergenes,
                        someRecipes
                )));
            };
        }
        return args -> {};
    }
}
