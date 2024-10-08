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

    // For demo purposes, we will seed the database with some data
    // The database is dropped, re-created and seeded every time the application starts
    // To disable this behaviour, set enableSeeder to false and in application.properties, set spring.jpa.hibernate.ddl-auto=update
    private boolean enableSeeder = true;

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
                        new Food(
                                "Nutella",
                                "spread",
                                "40084053",
                                LocalDateTime.now(),
                                Collections.emptyList(),
                                546,
                                32,
                                12,
                                58,
                                56,
                                7,
                                6,
                                0,
                                "A",
                                42,
                                "https://www.italiangourmet.de/cdn/shop/products/ferrero-streichfahige-creme-ferrero-nutella-kakao-und-haselnuss-creme-950g-8000500357729-23571880542387.jpg?v=1650900252&width=240"
                        )
                );

                myFood.add(
                        new Food(
                                "American Sandwich",
                                "bread",
                                "20139155",
                                LocalDateTime.now(),
                                Collections.emptyList(),
                                250,
                                4,
                                1,
                                46,
                                5,
                                3,
                                8,
                                1,
                                "B",
                                33,
                                "https://m.media-amazon.com/images/I/71VWXGpuRfL._SL1500_.jpg"
                        )
                );

                myFood.add(
                        new Food(
                                "Coca Cola",
                                "soft drink",
                                "54491472",
                                LocalDateTime.now(),
                                Collections.emptyList(),
                                42,
                                0,
                                0,
                                11,
                                11,
                                0,
                                0,
                                0,
                                "C",
                                24,
                                "https://www.coca-cola.com/content/dam/onexp/de/de/home-images/brand-coca-cola/de_coca-cola-original-taste_750x750.jpg/width2674.jpg"
                        )
                );

                Set<Allergen> myAllergens = new HashSet<>(allergenRepository.findAll().subList(0, 3));

                List<Recipe> myRecipes = new ArrayList<>();

                User newUser = new User(
                        "timwagner997@gmail.com",
                        new Fridge(),
                        myAllergens,
                        myRecipes
                );

                log.info("Preloading " + userRepository.save(newUser));
            };
        }

        return args -> {};
    }
}
