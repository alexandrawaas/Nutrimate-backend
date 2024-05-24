package com.example.nutrimatebackend.dtos.food;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class FoodDTORequest {
    @JsonProperty("amount") private int amount;
    @JsonProperty("barcode") private String barcode;
    @JsonProperty("expireDate") private LocalDateTime expireDate;

    public FoodDTORequest(int amount, String barcode, LocalDateTime expireDate) {
        this.amount = amount;
        this.barcode = barcode;
        this.expireDate = expireDate;
    }

    public int getAmount(){
        return amount;
    }
    public String getBarcode() {
        return barcode;
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }
}
