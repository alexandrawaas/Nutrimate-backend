package com.example.nutrimatebackend.services;

import com.example.nutrimatebackend.dtos.api.OpenFoodFactsResponse;
import com.example.nutrimatebackend.dtos.environmentalScore.EnvironmentalScoreDTOResponse;
import com.example.nutrimatebackend.dtos.food.FoodConverter;
import com.example.nutrimatebackend.dtos.food.FoodDTORequest;
import com.example.nutrimatebackend.dtos.food.FoodDTOResponse;
import com.example.nutrimatebackend.dtos.food.FoodScanDTOResponse;
import com.example.nutrimatebackend.entities.Food;
import com.example.nutrimatebackend.repositories.FoodRepository;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodService {

    private final FoodRepository foodRepository;
    private final FoodConverter foodConverter;
    private final WebClient webClient;


    public FoodService(FoodRepository foodRepository, FoodConverter foodConverter, WebClient webClient) {
        this.foodRepository = foodRepository;
        this.foodConverter = foodConverter;
        this.webClient = webClient;
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

    public FoodScanDTOResponse getFoodByBarcode(String barcode) {
        String path = String.format("/api/v0/product/%s.json", barcode);

        String url = new URIBuilder()
                .setScheme("https")
                .setHost("en.openfoodfacts.org")
                .setPath(path)
                .toString();

        OpenFoodFactsResponse response = webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(OpenFoodFactsResponse.class)
                .block();

        return foodConverter.convertServerResponseToDtoResponse(response);
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
