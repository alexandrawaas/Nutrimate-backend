package com.example.nutrimatebackend.utils;

import com.example.nutrimatebackend.entities.*;
import com.example.nutrimatebackend.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.*;

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
                log.info("Preloading " + allergenRepository.save(new Allergen("Soybeans")));
                log.info("Preloading " + allergenRepository.save(new Allergen("Nuts")));
                log.info("Preloading " + allergenRepository.save(new Allergen("Eggs")));
                log.info("Preloading " + allergenRepository.save(new Allergen("Fish")));
                log.info("Preloading " + allergenRepository.save(new Allergen("Mustard")));
                log.info("Preloading " + allergenRepository.save(new Allergen("Celery")));
                log.info("Preloading " + allergenRepository.save(new Allergen("Sulphur Dioxide and Sulphites")));
                log.info("Preloading " + allergenRepository.save(new Allergen("Lupin")));
                log.info("Preloading " + allergenRepository.save(new Allergen("Molluscs")));
                log.info("Preloading " + allergenRepository.save(new Allergen("Crustaceans")));


                List<Food> myFood = new ArrayList<>();

                myFood.add(
                        new Food("Nutella", "spread", "123456789", LocalDateTime.now(), Collections.emptyList(),
                                546, 32, 12, 58, 56, 7, 6, 0, "A", 42)
                );

                myFood.add(
                        new Food("American Sandwich", "bread", "123456789", LocalDateTime.now(), Collections.emptyList(),
                                250, 4, 1, 46, 5, 3, 8, 1, "B", 33)
                );

                myFood.add(
                        new Food("Coca Cola", "soft drink", "123456789", LocalDateTime.now(), Collections.emptyList(),
                                42, 0, 0, 11, 11, 0, 0, 0, "C", 24)
                );

                Set<Allergen> myAllergens = new HashSet<>(allergenRepository.findAll().subList(0, 3));

                List<Recipe> myRecipes = new ArrayList<>();

                User newUser = new User(
                        "timwagner997@gmail.com",
                        new Fridge(
                                myFood
                        ),
                        myAllergens,
                        myRecipes
                );

                log.info("Preloading " + userRepository.save(newUser));
            };
        }

        return args -> {};
    }
}
