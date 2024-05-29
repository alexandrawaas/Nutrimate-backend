package com.example.nutrimatebackend.dtos.food;

import com.example.nutrimatebackend.dtos.api.OpenFoodFactsResponse;
import com.example.nutrimatebackend.entities.Food;
import org.springframework.stereotype.Service;

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
                response.getNutriments().getEnergyKcal_100g(),
                response.getNutriments().getFat_100g(),
                response.getNutriments().getSaturatedFat_100g(),
                response.getNutriments().getCarbohydrates_100g(),
                response.getNutriments().getSugars_100g(),
                response.getNutriments().getFiber_100g(),
                response.getNutriments().getProteins_100g(),
                response.getNutriments().getSalt_100g(),
                response.getProduct().getAllergens_tags(),
                response.getProduct().getCategories_tags().getLast()
        );
    }

    public Food convertToEntity(FoodDTORequest foodDtoRequest) {
        Food food = new Food();
        food.setBarcode(foodDtoRequest.getBarcode());
        food.setExpireDate(foodDtoRequest.getExpireDate());
        return food;
    }


}
