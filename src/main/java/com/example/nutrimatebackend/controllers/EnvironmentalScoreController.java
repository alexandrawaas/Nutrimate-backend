package com.example.nutrimatebackend.controllers;

import com.example.nutrimatebackend.entities.Food;
import com.example.nutrimatebackend.repositories.FoodRepository;
import com.example.nutrimatebackend.repositories.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EnvironmentalScoreController {
    private final UserRepository userRepository;

    public EnvironmentalScoreController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/environmental-score")
    public int getEnvironmentalScore()
    {
        Long currentUser = 1L;

        int environmentalScore = 0;

        List<Food> allFoods = userRepository
                .findById(currentUser)
                .orElseThrow()
                .getFridge()
                .getContent();

        for (Food food : allFoods) {
            environmentalScore += food.calculateEnvironmentalScore();
        }

        return environmentalScore / allFoods.size();
    }
}
