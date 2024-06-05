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
                food.getExpireDate(),
                food.getCategory(),
                food.getName(),
                food.isOpen(),
                food.getDaysToConsume(),
                food.getCalories(),
                food.getFats(),
                food.getSaturatedFats(),
                food.getCarbs(),
                food.getSugar(),
                food.getFibers(),
                food.getProteins(),
                food.getSalt(),
                food.getAllergens()
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
                response.getProduct().getAllergensTags(),
                response.getProduct().getCategoriesTags().getLast(),
                response.getProduct().getProductName()
        );
    }

    public Food convertToEntity(FoodDTORequest foodDtoRequest) {
        Food food = new Food();
        food.setBarcode(foodDtoRequest.getBarcode());
        food.setExpireDate(foodDtoRequest.getExpireDate());
        return food;
    }


}
