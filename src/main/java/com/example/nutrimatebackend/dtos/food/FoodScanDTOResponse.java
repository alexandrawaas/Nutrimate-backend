package com.example.nutrimatebackend.dtos.food;

import com.example.nutrimatebackend.controllers.FoodController;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.List;

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

    public FoodScanDTOResponse(String barcode, double calories, double fat, double saturatedFats, double carbs, double sugar, double fibers, double protein, double salt, List<String> allergens, String category, String name) {
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
    }

    public void addLinks(String barcode) {
        add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FoodController.class).getFoodByBarcode(barcode)).withSelfRel());
    }
}
