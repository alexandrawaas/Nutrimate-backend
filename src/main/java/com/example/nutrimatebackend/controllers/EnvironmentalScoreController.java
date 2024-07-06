package com.example.nutrimatebackend.controllers;

import com.example.nutrimatebackend.dtos.environmentalScore.EnvironmentalScoreDTOResponse;
import com.example.nutrimatebackend.entities.Fridge;
import com.example.nutrimatebackend.repositories.UserRepository;
import com.example.nutrimatebackend.services.EnvironmentalScoreService;
import com.example.nutrimatebackend.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnvironmentalScoreController {
    private final UserRepository userRepository;
    private final UserService userService;

    public EnvironmentalScoreController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping(value = "/fridge/environmental-score")
    public EnvironmentalScoreDTOResponse getEnvironmentalScore()
    {
        Long currentUserId = userService.getCurrentUser().getId();

        Fridge fridge = userRepository
                .findById(currentUserId)
                .orElseThrow()
                .getFridge();

        int environmentalScore = new EnvironmentalScoreService()
                .calculateEnvironmentalScore(fridge);

        return new EnvironmentalScoreDTOResponse(environmentalScore);
    }
}
