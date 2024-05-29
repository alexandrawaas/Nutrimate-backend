package com.example.nutrimatebackend.dtos.food;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FoodScanDTOResponse {






    private String barcode;

    public FoodScanDTOResponse(String barcode) {
        this.barcode = barcode;
    }
}
