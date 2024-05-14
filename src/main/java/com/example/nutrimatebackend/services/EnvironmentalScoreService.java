package com.example.nutrimatebackend.services;

import com.example.nutrimatebackend.entities.Food;
import com.example.nutrimatebackend.entities.Fridge;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnvironmentalScoreService
{
    public int calculateEnvironmentalScore(Fridge fridge) {
        List<Food> foods = fridge.getContent();

        int sum = 0;

        for (Food food : foods) {
            sum += food.calculateEnvironmentalScore();
        }

        return sum / foods.size();
    }
}
