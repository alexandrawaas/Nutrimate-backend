package com.example.nutrimatebackend.services;

import com.example.nutrimatebackend.dtos.allergen.AllergenConverter;
import com.example.nutrimatebackend.dtos.allergen.AllergenDTOResponse;
import com.example.nutrimatebackend.dtos.api.openFoodFacts.OpenFoodFactsResponse;
import com.example.nutrimatebackend.dtos.environmentalScore.EnvironmentalScoreDTOResponse;
import com.example.nutrimatebackend.dtos.food.*;
import com.example.nutrimatebackend.entities.Allergen;
import com.example.nutrimatebackend.entities.Food;
import com.example.nutrimatebackend.entities.User;
import com.example.nutrimatebackend.repositories.AllergenRepository;
import com.example.nutrimatebackend.repositories.FoodRepository;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;

@Service
public class FoodService {
    private final FoodRepository foodRepository;
    private final FoodConverter foodConverter;
    private final WebClient webClient;
    private final AllergenRepository allergenRepository;
    private final FoodAssembler foodAssembler;
    private final AllergenConverter allergenConverter;
    private final UserService userService;
    public FoodService(FoodRepository foodRepository, FoodConverter foodConverter, WebClient webClient, AllergenRepository allergenRepository, AllergenConverter allergenConverter, UserService userService, FoodAssembler foodAssembler)
    {
        this.foodRepository = foodRepository;
        this.foodConverter = foodConverter;
        this.webClient = webClient;
        this.allergenRepository = allergenRepository;
        this.foodAssembler = foodAssembler;
        this.allergenConverter = allergenConverter;
        this.userService = userService;
    public PagedModel<FoodDTOResponse> getAllFoodPaginated(Pageable pageable) {
        Page<Food> response = foodRepository.findAll(pageable);
        return foodAssembler.toPagedModel(response);
    }

    public List<FoodDTOResponse> getAllFood() {
        return userService.getCurrentUser().getFridge().getContent().stream().map(foodConverter::convertToDtoResponse).toList();
    }

    public List<FoodDTOResponse> createFood(FoodDTORequest foodDTORequest) {
        int amount = foodDTORequest.getAmount();
        List<FoodDTOResponse> dtoResponseList = new ArrayList<>();

        FoodScanDTOResponse foodRequest = getFoodByBarcode(foodDTORequest.getBarcode());

        List<Allergen> allergens = new ArrayList<>();

        for (String allergenName : foodRequest.getAllergens()) {
            Allergen allergen = allergenRepository.findByNameIgnoreCase(allergenName.replaceFirst("en:", ""));
            if(allergen == null) {
                System.out.println("Allergen not found: " + allergenName);
            }
            allergens.add(allergen);
        }

        for (int i = 0; i < amount; i++) {
            Food foodEntity = foodConverter.convertToEntity(foodDTORequest);

            foodEntity.setName(foodRequest.getName());
            foodEntity.setCategory(foodRequest.getCategory().replaceFirst("en:", "").replace("-", " "));
            foodEntity.setCalories(foodRequest.getCalories());
            foodEntity.setFats(foodRequest.getFat());
            foodEntity.setCarbs(foodRequest.getCarbs());
            foodEntity.setProteins(foodRequest.getProtein());
            foodEntity.setSalt(foodRequest.getSalt());
            foodEntity.setFibers(foodRequest.getFibers());
            foodEntity.setSaturatedFats(foodRequest.getSaturatedFats());
            foodEntity.setSugar(foodRequest.getSugar());
            foodEntity.setAllergens(allergens);

            User user = userService.getCurrentUser();
            user.getFridge().getContent().add(foodEntity);
            userService.saveUser(user);
            dtoResponseList.add(foodConverter.convertToDtoResponse(foodEntity));
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

    public FoodDTOResponse deleteFood(Long id) {
        User user = userService.getCurrentUser();
        Food food = user.getFridge().getContent().stream().filter(f -> f.getId().equals(id)).findFirst().orElseThrow(() -> new RuntimeException("Food not found"));
        user.getFridge().getContent().remove(food);
        userService.saveUser(user);
        //foodRepository.deleteById(id); //Throws error //TODO: delete the food from the database
        return foodConverter.convertToDtoResponse(food);
    }

    public EnvironmentalScoreDTOResponse getEnvironmentalScore(String barcode) {
        Food food = foodConverter.convertScanDtoToEntity(getFoodByBarcode(barcode));
        int score = food.calculateEnvironmentalScore();
        return new EnvironmentalScoreDTOResponse(score);
    }

    public List<AllergenDTOResponse> getMatchingAllergensByFoodBarcode(String barcode) {
        Optional<Food> food = foodRepository.findByBarcode(barcode);
        List<Allergen> foodAllergens = new ArrayList<>();
        if (food.isEmpty()) {
            foodAllergens = getFoodByBarcode(barcode).getAllergens().stream().map(allergenName -> allergenRepository.findByNameIgnoreCase(allergenName.substring(3))).toList();
        }
        else {
            foodAllergens = food.get().getAllergens();
        }
        Set<Allergen> userAllergens = userService.getCurrentUser().getAllergens();
        Set<Allergen> matchingAllergens= userAllergens.stream().filter(foodAllergens::contains).collect(Collectors.toSet());

        return matchingAllergens.stream().map(allergenConverter::convertToDTOResponse).toList();
    }

}
