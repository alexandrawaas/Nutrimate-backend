package com.example.nutrimatebackend.dtos.food;

import com.example.nutrimatebackend.entities.Food;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class FoodDTOResponse {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("barcode")
    private String barcode;
    @JsonProperty("expireDate")
    private LocalDateTime expireDate;

    public FoodDTOResponse(Long id, String barcode, LocalDateTime expireDate) {
        this.id = id;
        this.barcode = barcode;
        this.expireDate = expireDate;
    }

    public Long getId() {
        return id;
    }

}
