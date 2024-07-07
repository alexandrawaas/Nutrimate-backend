package com.example.nutrimatebackend.dtos.food;

import com.example.nutrimatebackend.controllers.FoodController;
import com.example.nutrimatebackend.entities.Allergen;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Data
public class FoodDTOResponse extends RepresentationModel<FoodDTOResponse>
{
    private Long id;
    private String barcode;
    private LocalDateTime expireDate;
    String category;
    String name;

    boolean isOpen;
    LocalDateTime timeOpened;

    Integer daysToConsume;

    double calories;
    double fats;
    double saturatedFats;
    double carbs;
    double sugar;
    double fibers;
    double proteins;
    double salt;

    List<Allergen> allergens;

    String imageUrl;

    public FoodDTOResponse(Long id, String barcode, LocalDateTime expireDate, String category, String name, boolean isOpen, LocalDateTime timeOpened, Integer daysToConsume, double calories, double fats, double saturatedFats, double carbs, double sugar, double fibers, double proteins, double salt, List<Allergen> allergens, String imageUrl) {
        this.id = id;
        this.barcode = barcode;
        this.expireDate = expireDate;
        this.category = category;
        this.name = name;
        this.isOpen = isOpen;
        this.timeOpened = timeOpened;
        this.daysToConsume = daysToConsume;
        this.calories = calories;
        this.fats = fats;
        this.saturatedFats = saturatedFats;
        this.carbs = carbs;
        this.sugar = sugar;
        this.fibers = fibers;
        this.proteins = proteins;
        this.salt = salt;
        this.allergens = allergens;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void addLinks(Long id) {
        add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FoodController.class).getFoodById(id, null)).withSelfRel());
    }

    @Override
    public int hashCode() {
        int[] array = new int[allergens.size()];
        for (int i = 0; i < allergens.size(); i++) {
            array[i] = allergens.get(i).hashCode();
        }
        return Objects.hash(id, barcode, expireDate, category, name, isOpen, calories, fats, saturatedFats, carbs, sugar, fibers, proteins, salt, imageUrl)+ Arrays.stream(array).sum();
    }
}
