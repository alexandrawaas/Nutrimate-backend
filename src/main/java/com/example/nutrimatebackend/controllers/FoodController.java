package com.example.nutrimatebackend.controllers;

import com.example.nutrimatebackend.entities.Food;
import com.example.nutrimatebackend.repositories.FoodRepository;

import java.time.LocalDateTime;
import java.util.List;

import com.example.nutrimatebackend.repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

@RestController
public class FoodController {
    private final FoodRepository foodRepository;
    private final UserRepository userRepository;

    public FoodController(FoodRepository foodRepository, UserRepository userRepository)
    {
        this.foodRepository = foodRepository;
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/food")
    public List<Food> getFood()
    {
        return userRepository.findById(1L).orElse(null).getFridge().getContent();
    }

    @PostMapping(path="/food")
    public @ResponseBody Food addNewFood (@RequestParam String name, @RequestParam String category, @RequestParam String barcode, @RequestParam LocalDateTime expireDate) {

        Food n = new Food();
        n.setName(name);
        n.setCategory(category);
        n.setBarcode(barcode);
        n.setExpireDate(expireDate);
        foodRepository.save(n);
        return n;
    }
    @GetMapping(value = "/food/scan/{barcode}")
    public Food getFoodByBarcode(@PathVariable String barcode)
    {
        return foodRepository.findByBarcode(barcode);
    }

    @PatchMapping("/food/{foodId}")
    public Food openFood(@PathVariable Long foodId, @RequestBody int daysToConsume)
    {
        Food food = foodRepository.findById(foodId).orElseThrow();
        food.setOpen(true);
        food.setDaysToConsume(daysToConsume);
        foodRepository.save(food);
        return food;
    }

    @GetMapping(value="/food/{foodId}")
    public Food getFoodById(@PathVariable Long foodId)
    {
        return foodRepository.findById(foodId).orElseThrow();
    }

    @DeleteMapping(value="/food/{foodId}")
    public void deleteFood(@PathVariable("foodId") Long id)
    {
        foodRepository.deleteById(id);
    }

    @GetMapping(value = "/food/{foodId}/environmental-score")
    public int getEnvironmentalScore(@PathVariable Long foodId)
    {
        return foodRepository.findById(foodId).orElseThrow().calculateEnvironmentalScore();
    }

}
