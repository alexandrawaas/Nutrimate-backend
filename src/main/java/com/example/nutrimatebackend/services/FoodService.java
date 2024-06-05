package com.example.nutrimatebackend.services;

import com.example.nutrimatebackend.dtos.api.openFoodFacts.OpenFoodFactsResponse;
import com.example.nutrimatebackend.dtos.environmentalScore.EnvironmentalScoreDTOResponse;
import com.example.nutrimatebackend.dtos.food.FoodConverter;
import com.example.nutrimatebackend.dtos.food.FoodDTORequest;
import com.example.nutrimatebackend.dtos.food.FoodDTOResponse;
import com.example.nutrimatebackend.dtos.food.FoodScanDTOResponse;
import com.example.nutrimatebackend.entities.Allergen;
import com.example.nutrimatebackend.entities.Food;
import com.example.nutrimatebackend.repositories.AllergenRepository;
import com.example.nutrimatebackend.repositories.FoodRepository;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class FoodService {

    private final FoodRepository foodRepository;
    private final FoodConverter foodConverter;
    private final WebClient webClient;
    private final AllergenRepository allergenRepository;


    public FoodService(FoodRepository foodRepository, FoodConverter foodConverter, WebClient webClient, AllergenRepository allergenRepository) {
        this.foodRepository = foodRepository;
        this.foodConverter = foodConverter;
        this.webClient = webClient;
        this.allergenRepository = allergenRepository;
    }

    public List<FoodDTOResponse> getAllFood() {
        return foodRepository.findAll().stream().map(foodConverter::convertToDtoResponse).toList();
    }

    public List<FoodDTOResponse> createFood(FoodDTORequest foodDTORequest) {
        int amount = foodDTORequest.getAmount();
        List<FoodDTOResponse> dtoResponseList = new ArrayList<>();

        FoodScanDTOResponse foodRequest = getFoodByBarcode(foodDTORequest.getBarcode());

        List<Allergen> allergens = new ArrayList<>();

        for (String allergenName : foodRequest.getAllergens()) {
            Allergen allergen = allergenRepository.findByName(allergenName);
            allergens.add(allergen);
        }

        for (int i = 0; i < amount; i++) {
            Food foodEntity = foodConverter.convertToEntity(foodDTORequest);

            foodEntity.setCalories(foodRequest.getCalories());
            foodEntity.setFats(foodRequest.getFat());
            foodEntity.setCarbs(foodRequest.getCarbs());
            foodEntity.setProteins(foodRequest.getProtein());
            foodEntity.setSalt(foodRequest.getSalt());
            foodEntity.setFibers(foodRequest.getFibers());
            foodEntity.setSaturatedFats(foodRequest.getSaturatedFats());
            foodEntity.setSugar(foodRequest.getSugar());

            foodEntity.setAllergens(allergens);

            dtoResponseList.add(foodConverter.convertToDtoResponse(
                    foodRepository.save(foodEntity)
            ));
        }

        return dtoResponseList;
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

        if (response == null) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Barcode not found");
        }

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
