package com.example.nutrimatebackend.controllers;

import com.example.nutrimatebackend.dtos.allergen.AllergenDTOResponse;
import com.example.nutrimatebackend.dtos.environmentalScore.EnvironmentalScoreDTOResponse;
import com.example.nutrimatebackend.dtos.food.DaysToConsumeRequestDTO;
import com.example.nutrimatebackend.dtos.food.FoodDTORequest;
import com.example.nutrimatebackend.dtos.food.FoodDTOResponse;
import com.example.nutrimatebackend.dtos.food.FoodScanDTOResponse;
import com.example.nutrimatebackend.services.FoodService;

import java.util.List;

import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class FoodController {
    private final FoodService foodService;

    private final CacheManager cacheManager;

    public FoodController(FoodService foodService, CacheManager cacheManager)
    {
        this.foodService = foodService;
        this.cacheManager = cacheManager;
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

    @Cacheable(value = "foodsCache", key = "#barcode")
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
    public ResponseEntity<FoodDTOResponse> getFoodById(@PathVariable Long foodId, @RequestHeader(value = "If-None-Match", required = false) String ifNoneMatch)
    {
        try {
            FoodDTOResponse foodDTOResponse =  foodService.getFoodById(foodId);
            foodDTOResponse.addLinks(foodId);

            String currentETag = calculateETag(foodDTOResponse);

            if (ifNoneMatch != null && ifNoneMatch.equals(currentETag)) {
                return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
            }

            return ResponseEntity.ok()
                    .header("ETag", currentETag)
                    .header("Cache-Control", "max-age=400") // 400 = 5 Minuten
                    .body(foodDTOResponse);
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

    private String calculateETag(FoodDTOResponse food) {
        return food.getId() + "--" + food.hashCode();
    }
}
