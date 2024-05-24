package com.example.nutrimatebackend.dtos.food;

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

    public Food convertToEntity(FoodDTORequest foodDtoRequest) {
        Food food = new Food();
        food.setBarcode(foodDtoRequest.getBarcode());
        food.setExpireDate(foodDtoRequest.getExpireDate());
        return food;
    }


}
