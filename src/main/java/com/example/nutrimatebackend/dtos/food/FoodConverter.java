package com.example.nutrimatebackend.dtos.food;

import com.example.nutrimatebackend.dtos.api.openFoodFacts.OpenFoodFactsResponse;
import com.example.nutrimatebackend.entities.Food;
import org.springframework.stereotype.Service;

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
                food.getAllergens(),
                food.getImageUrl()
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
                response.getProduct().getAllergensTags().stream().map(allergen -> allergen.replaceFirst("en:", "").replace("-", " ")).toList(),
                response.getProduct().getCategoriesTags().getLast().replaceFirst("en:", "").replace("-", " "),
                response.getProduct().getProductName(),
                response.getProduct().getEcoscoreGrade(),
                response.getProduct().getEcoscoreScore(),
                response.getProduct().getSelectedImages().getFront().getDisplay().getEn() != null ? response.getProduct().getSelectedImages().getFront().getDisplay().getEn() : response.getProduct().getSelectedImages().getFront().getDisplay().getDe()
        );
    }

    public Food convertToEntity(FoodDTORequest foodDtoRequest) {
        Food food = new Food();
        food.setBarcode(foodDtoRequest.getBarcode());
        food.setExpireDate(foodDtoRequest.getExpireDate());
        return food;
    }

    public Food convertScanDtoToEntity(FoodScanDTOResponse foodScanDTOResponse) {
        Food food = new Food();
        food.setBarcode(foodScanDTOResponse.getBarcode());
        food.setCalories(foodScanDTOResponse.getCalories());
        food.setFats(foodScanDTOResponse.getFat());
        food.setSaturatedFats(foodScanDTOResponse.getSaturatedFats());
        food.setCarbs(foodScanDTOResponse.getCarbs());
        food.setSugar(foodScanDTOResponse.getSugar());
        food.setFibers(foodScanDTOResponse.getFibers());
        food.setProteins(foodScanDTOResponse.getProtein());
        food.setSalt(foodScanDTOResponse.getSalt());
        food.setCategory(foodScanDTOResponse.getCategory());
        food.setName(foodScanDTOResponse.getName());
        food.setImageUrl(foodScanDTOResponse.getImageUrl());
        return food;
    }


}
