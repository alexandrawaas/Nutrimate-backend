package com.example.nutrimatebackend.controllers;

import com.example.nutrimatebackend.dtos.environmentalScore.EnvironmentalScoreDTOResponse;
import com.example.nutrimatebackend.entities.Fridge;
import com.example.nutrimatebackend.repositories.UserRepository;
import com.example.nutrimatebackend.services.EnvironmentalScoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnvironmentalScoreController {
    private final UserRepository userRepository;

    public EnvironmentalScoreController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/fridge/environmental-score")
    public EnvironmentalScoreDTOResponse getEnvironmentalScore()
    {
        // TODO: Replace this with the current logged in user!
        Long currentUser = 1L;

        Fridge fridge = userRepository
                .findById(currentUser)
                .orElseThrow()
                .getFridge();

        int environmentalScore = new EnvironmentalScoreService()
                .calculateEnvironmentalScore(fridge);

        return new EnvironmentalScoreDTOResponse(environmentalScore);
    }
}
