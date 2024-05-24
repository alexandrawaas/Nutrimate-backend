package com.example.nutrimatebackend.services;

import com.example.nutrimatebackend.dtos.environmentalScore.EnvironmentalScoreDTOResponse;
import com.example.nutrimatebackend.dtos.food.FoodConverter;
import com.example.nutrimatebackend.dtos.food.FoodDTORequest;
import com.example.nutrimatebackend.dtos.food.FoodDTOResponse;
import com.example.nutrimatebackend.entities.Food;
import com.example.nutrimatebackend.repositories.FoodRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodService {

    private final FoodRepository foodRepository;
    private final FoodConverter foodConverter;


    public FoodService(FoodRepository foodRepository, FoodConverter foodConverter) {
        this.foodRepository = foodRepository;
        this.foodConverter = foodConverter;
    }

    public List<FoodDTOResponse> getAllFood() {
        return foodRepository.findAll().stream().map(foodConverter::convertToDtoResponse).toList();
    }

    public List<FoodDTOResponse> createFood(FoodDTORequest foodDTORequest) {
        int amount = foodDTORequest.getAmount();
        List<FoodDTOResponse> list = new ArrayList<FoodDTOResponse>();
        for(int i=0; i<amount; i++){
            list.add(foodConverter.convertToDtoResponse(foodRepository.save(foodConverter.convertToEntity(foodDTORequest))));
        }
        return list;
    }

    public FoodDTOResponse getFoodByBarcode(String barcode) {
        Food food = foodRepository.findByBarcode(barcode).orElseThrow(() -> new RuntimeException("Food not found"));
        return foodConverter.convertToDtoResponse(food);
    }

    public FoodDTOResponse openFood(Long foodId, int daysToConsume) {
        Food food = foodRepository.findById(foodId).orElseThrow(() -> new RuntimeException("Food not found"));
        food.setOpen(true);
        food.setDaysToConsume(daysToConsume);
        foodRepository.save(food);
        return foodConverter.convertToDtoResponse(food);
    }

    public FoodDTOResponse getFoodById(Long foodId) {
        Food food = foodRepository.findById(foodId).orElseThrow(() -> new RuntimeException("Food not found"));
        return foodConverter.convertToDtoResponse(food);
    }

    public void deleteFood(Long id) {
        foodRepository.deleteById(id);
    }

    public EnvironmentalScoreDTOResponse getEnvironmentalScore(Long foodId) {
        Food food = foodRepository.findById(foodId).orElseThrow(() -> new RuntimeException("Food not found"));
        int score = food.calculateEnvironmentalScore();
        return new EnvironmentalScoreDTOResponse(score);
    }

}
