package com.example.nutrimatebackend.dtos.food;

import com.example.nutrimatebackend.dtos.api.OpenFoodFactsResponse;
import com.example.nutrimatebackend.entities.Food;
import com.example.nutrimatebackend.repositories.FoodRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
                response.getNutriments().getEnergyKcal100g(),
                response.getNutriments().getFat100g(),
                response.getNutriments().getSaturatedFat100g(),
                response.getNutriments().getCarbohydrates100g(),
                response.getNutriments().getSugars100g(),
                response.getNutriments().getFiber100g(),
                response.getNutriments().getProteins100g(),
                response.getNutriments().getSalt100g(),
                response.getProduct().getAllergensTags(),
                response.getProduct().getCategoriesTags().getLast()
        );
    }

    public Food convertToEntity(FoodDTORequest foodDtoRequest) {
        Food food = new Food();
        food.setBarcode(foodDtoRequest.getBarcode());
        food.setExpireDate(foodDtoRequest.getExpireDate());
        return food;
    }


}
