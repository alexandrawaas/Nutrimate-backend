package com.example.nutrimatebackend.dtos.api.edamam;

import lombok.Data;

import java.util.List;

@Data
public class EdamamResponse {
    private List<Hit> hits;
}
