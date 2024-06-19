package com.example.nutrimatebackend.controllers;

import com.example.nutrimatebackend.dtos.allergen.AllergenDTOResponse;
import com.example.nutrimatebackend.dtos.environmentalScore.EnvironmentalScoreDTOResponse;
import com.example.nutrimatebackend.dtos.food.DaysToConsumeRequestDTO;
import com.example.nutrimatebackend.dtos.food.FoodDTORequest;
import com.example.nutrimatebackend.dtos.food.FoodDTOResponse;
import com.example.nutrimatebackend.dtos.food.FoodScanDTOResponse;
import com.example.nutrimatebackend.services.FoodService;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.cache.annotation.Cacheable;
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

    @GetMapping(value = "/fridge/food")
    public PagedModel<FoodDTOResponse> getAllFoodPaginated(Pageable pageable, @RequestParam(required = false) String q) {
        return foodService.getAllFoodPaginated(pageable, q);
    }

    public List<FoodDTOResponse> getAllFood() {
        return foodService.getAllFood();
    }

    @PostMapping(path="/fridge/food")
    @ResponseStatus(HttpStatus.CREATED)
    List<FoodDTOResponse> createFood(@RequestBody FoodDTORequest foodDtoRequest) {
        return foodService.createFood(foodDtoRequest);
    }

    @Cacheable("food")
    @GetMapping(value = "/food/{barcode}")
    public FoodScanDTOResponse getFoodByBarcode(@PathVariable String barcode)
    {
        try {
            FoodScanDTOResponse response = foodService.getFoodByBarcode(barcode);
            response.addLinks(barcode);
            return response;
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PatchMapping(value = "/fridge/food/{foodId}")
    FoodDTOResponse openFood(@RequestBody DaysToConsumeRequestDTO daysToConsumeDTO, @PathVariable Long foodId)
    {
        try {
            FoodDTOResponse response = foodService.openFood(foodId, daysToConsumeDTO.getDaysToConsume());
            response.addLinks(foodId);
            return response;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Food not found");
        }
    }

    @GetMapping(value="/fridge/food/{foodId}")
    public FoodDTOResponse getFoodById(@PathVariable Long foodId)
    {
        try {
            FoodDTOResponse foodDTOResponse =  foodService.getFoodById(foodId);
            foodDTOResponse.addLinks(foodId);
            return foodDTOResponse;
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping(value="/fridge/food/{foodId}")
    FoodDTOResponse deleteFood(@PathVariable("foodId") Long id)
    {
        try
        {
            return foodService.deleteFood(id);
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(value = "/food/{barcode}/environmental-score")
    EnvironmentalScoreDTOResponse getEnvironmentalScore(@PathVariable String barcode)
    {
        try {
            return foodService.getEnvironmentalScore(barcode);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    @GetMapping(value = "/food/{barcode}/matching-allergens")
    public List<AllergenDTOResponse> getMatchingAllergens(@PathVariable String barcode)
    {
        try {
            return foodService.getMatchingAllergensByFoodBarcode(barcode);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
