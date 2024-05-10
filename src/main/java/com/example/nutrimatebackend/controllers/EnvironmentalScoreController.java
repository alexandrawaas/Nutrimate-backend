package com.example.nutrimatebackend.controllers;

import com.example.nutrimatebackend.dtos.EnvironmentalScoreDTO;
import com.example.nutrimatebackend.entities.Food;
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
    public EnvironmentalScoreDTO getEnvironmentalScore()
    {
        // TODO: Replace this with the current logged in user!

        // TODO: put this inside a service
        Long currentUser = 1L;

        int environmentalScoreSum = 0;

        List<Food> allFoods = userRepository
                .findById(currentUser)
                .orElseThrow()
                .getFridge()
                .getContent();

        for (Food food : allFoods) {
            environmentalScoreSum += food.calculateEnvironmentalScore();
        }

        return new EnvironmentalScoreDTO(
                environmentalScoreSum / allFoods.size()
        );
    }
}
