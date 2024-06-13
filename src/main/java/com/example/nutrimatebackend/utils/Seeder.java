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

                Set<Allergen> myAllergens = new HashSet<>(allergenRepository.findAll().subList(0, 3));

                List<Recipe> myRecipes = new ArrayList<>();

                User user = userRepository.save(new User(
                        "dummyuser@email.com",
                        "securePassword123",
                        new Fridge(),
                        myAllergens,
                        myRecipes
                ));
                List<Food> myFood = new ArrayList<>();

                myFood.add(
                        new Food("Nutella", "spread", "123456789", LocalDateTime.now(), Collections.emptyList(), 1,2,3,4,5,6,7,8, "A", 42)
                );

                myFood.add(
                        new Food("American Sandwich", "bread", "123456789", LocalDateTime.now(), Collections.emptyList(), 1,2,3,4,5,6,7,8, "B", 33)
                );

                myFood.add(
                        new Food("Coke", "soft drink", "123456789", LocalDateTime.now(), Collections.emptyList(), 1,2,3,4,5,6,7,8, "C", 24)
                );

                user.getFridge().setContent(myFood);

                userRepository.save(user);


                user.getFridge().getContent().forEach(food -> {
                    food.setFridge(user.getFridge());
                    foodRepository.save(food);
                });
            };
        }
        return args -> {};
    }
}
