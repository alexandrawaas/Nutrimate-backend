package com.example.nutrimatebackend.dtos.food;

import com.example.nutrimatebackend.dtos.api.openFoodFacts.OpenFoodFactsResponse;
import com.example.nutrimatebackend.entities.Food;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class FoodConverter {

    public FoodDTOResponse convertToDtoResponse(Food food) {
        return new FoodDTOResponse(
                food.getId(),
                food.getBarcode(),
                food.getExpireDate()
        );
    }

    public FoodScanDTOResponse convertServerResponseToDtoResponse(OpenFoodFactsResponse response) {

        return new FoodScanDTOResponse(
                response.getCode(),
                response.getProduct().getNutriments().getEnergyKcal100g(),
                response.getProduct().getNutriments().getFat100g(),
                response.getProduct().getNutriments().getSaturatedFat100g(),
                response.getProduct().getNutriments().getCarbohydrates100g(),
                response.getProduct().getNutriments().getSugars100g(),
                response.getProduct().getNutriments().getFiber100g(),
                response.getProduct().getNutriments().getProteins100g(),
                response.getProduct().getNutriments().getSalt100g(),
                Arrays.stream(response.getProduct().getAllergensTags()).toList(),
                Arrays.stream(response.getProduct().getCategoriesTags()).toList().getLast()
        );
    }

    public Food convertToEntity(FoodDTORequest foodDtoRequest) {
        Food food = new Food();
        food.setBarcode(foodDtoRequest.getBarcode());
        food.setExpireDate(foodDtoRequest.getExpireDate());
        return food;
    }


}
