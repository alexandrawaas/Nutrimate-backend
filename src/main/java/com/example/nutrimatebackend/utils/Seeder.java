package com.example.nutrimatebackend.utils;

import com.example.nutrimatebackend.entities.*;
import com.example.nutrimatebackend.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
class Seeder {

    private boolean enableSeeder = true;
    // TODO: Drop Database if enableSeeder is true

    private static final Logger log = LoggerFactory.getLogger(Seeder.class);

    @Bean
    CommandLineRunner initDatabase(
            AllergenRepository allergenRepository,
            UserRepository userRepository,
            FridgeRepository fridgeRepository,
            FoodRepository foodRepository,
            RecipeRepository recipeRepository
    ) {
        if(enableSeeder) {
            return args -> {
                // Seed categories
                log.info("Preloading " + allergenRepository.save(new Allergen("Milk")));
                log.info("Preloading " + allergenRepository.save(new Allergen("Peanuts")));
                log.info("Preloading " + allergenRepository.save(new Allergen("Gluten")));
                log.info("Preloading " + allergenRepository.save(new Allergen("Shellfish")));
                log.info("Preloading " + allergenRepository.save(new Allergen("Soy")));
                log.info("Preloading " + allergenRepository.save(new Allergen("Tree Nuts")));
                log.info("Preloading " + allergenRepository.save(new Allergen("Eggs")));
                log.info("Preloading " + allergenRepository.save(new Allergen("Fish")));
                log.info("Preloading " + allergenRepository.save(new Allergen("Mustard")));
                log.info("Preloading " + allergenRepository.save(new Allergen("Celery")));
                log.info("Preloading " + allergenRepository.save(new Allergen("Sulphur Dioxide and Sulphites")));
                log.info("Preloading " + allergenRepository.save(new Allergen("Lupin")));
                log.info("Preloading " + allergenRepository.save(new Allergen("Molluscs")));
                log.info("Preloading " + allergenRepository.save(new Allergen("Crustaceans")));



                // Seed some foods
                log.info("Preloading " + foodRepository.save(
                        new Food("Nutella", "spread", "3017620422003", LocalDateTime.now(), Collections.emptyList(), 1,2,3,4,5,6,7,8)
                ));

                log.info("Preloading " + foodRepository.save(
                        new Food("American Sandwich", "bread", "123456789", LocalDateTime.now(), Collections.emptyList(), 1,2,3,4,5,6,7,8)
                ));

                log.info("Preloading " + foodRepository.save(
                        new Food("Coke", "soft drink", "123456789", LocalDateTime.now(), Collections.emptyList(), 1,2,3,4,5,6,7,8)
                ));

                List<Food> myFood = foodRepository.findAll();

                Fridge myFridge = new Fridge(myFood);

                log.info("Preloading " + fridgeRepository.save(myFridge));

                myFridge = fridgeRepository.findAll().getFirst();

                Set<Allergen> someAllergenes = new HashSet<>(allergenRepository.findAll().subList(0, 3));

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
