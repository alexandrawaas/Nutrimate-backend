package com.example.nutrimatebackend.dtos.food;

import com.example.nutrimatebackend.controllers.FoodController;
import com.example.nutrimatebackend.dtos.api.openFoodFacts.SelectedImages;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Data
public class FoodScanDTOResponse extends RepresentationModel<FoodScanDTOResponse>
{
    private String name;
    private String barcode;
    private double calories;
    private double fat;
    private double saturatedFats;
    private double carbs;
    private double sugar;
    private double fibers;
    private double protein;
    private double salt;

    private List<String> allergens;

    private String category;

    private String imageUrl;

    // environmental data
    String ecoscoreGrade;
    int ecoscoreScore;

    public FoodScanDTOResponse(String barcode, double calories, double fat, double saturatedFats, double carbs, double sugar, double fibers, double protein, double salt, List<String> allergens, String category, String name, String ecoscoreGrade, int ecoscoreScore, String imageUrl) {
        this.barcode = barcode;
        this.calories = calories;
        this.fat = fat;
        this.saturatedFats = saturatedFats;
        this.carbs = carbs;
        this.sugar = sugar;
        this.fibers = fibers;
        this.protein = protein;
        this.salt = salt;
        this.allergens = allergens;
        this.category = category;
        this.name = name;
        this.ecoscoreGrade = ecoscoreGrade;
        this.ecoscoreScore = ecoscoreScore;
        this.imageUrl = imageUrl;
    }

    public void addLinks(String barcode) {
        add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FoodController.class).getFoodByBarcode(barcode, null)).withSelfRel());
    }

    @Override
    public int hashCode() {
        int[] array = new int[allergens.size()];
        for (int i = 0; i < allergens.size(); i++) {
            array[i] = allergens.get(i).hashCode();
        }
        return Objects.hash(barcode, category, name, calories, fat, saturatedFats, carbs, sugar, fibers, protein, salt, imageUrl, ecoscoreGrade, ecoscoreScore)+ Arrays.stream(array).sum();
    }
}
