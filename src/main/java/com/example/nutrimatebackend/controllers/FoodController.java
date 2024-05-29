package com.example.nutrimatebackend.controllers;

import com.example.nutrimatebackend.dtos.environmentalScore.EnvironmentalScoreDTOResponse;
import com.example.nutrimatebackend.dtos.food.FoodDTORequest;
import com.example.nutrimatebackend.dtos.food.FoodDTOResponse;
import com.example.nutrimatebackend.dtos.food.FoodScanDTOResponse;
import com.example.nutrimatebackend.services.FoodService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class FoodController {
    private final FoodService foodService;

    public FoodController(FoodService foodService)
    {
        this.foodService = foodService;
    }

    @GetMapping(value = "/food")
    List<FoodDTOResponse> getAllFood() {
        return foodService.getAllFood();
    }

    @PostMapping(path="/food")
    List<FoodDTOResponse> createFood(@RequestBody FoodDTORequest foodDtoRequest) {
        return foodService.createFood(foodDtoRequest);
    }

    @GetMapping(value = "/food/scan/{barcode}")
    FoodScanDTOResponse getFoodByBarcode(@PathVariable String barcode)
    {
        try {
            return foodService.getFoodByBarcode(barcode);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PatchMapping("/food/{foodId}")
    FoodDTOResponse openFood(@PathVariable Long foodId, @RequestBody int daysToConsume)
    {
        try {
            return foodService.openFood(foodId, daysToConsume);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Food not found");
        }
    }

    @GetMapping(value="/food/{foodId}")
    FoodDTOResponse getFoodById(@PathVariable Long foodId)
    {
        try {
            return foodService.getFoodById(foodId);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping(value="/food/{foodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteFood(@PathVariable("foodId") Long id)
    {
        foodService.deleteFood(id);
    }

    @GetMapping(value = "/food/{foodId}/environmental-score")
    EnvironmentalScoreDTOResponse getEnvironmentalScore(@PathVariable Long foodId)
    {
        try {
            return foodService.getEnvironmentalScore(foodId);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
