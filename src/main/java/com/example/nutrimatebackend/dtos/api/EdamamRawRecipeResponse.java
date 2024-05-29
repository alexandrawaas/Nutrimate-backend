package com.example.nutrimatebackend.dtos.api;

import lombok.Data;

import java.util.List;

@Data
public class EdamamRawRecipeResponse {
    private List<Hit> hits;

    @Data
    public static class Hit {
        private Recipe recipe;
    }

    @Data
    public static class Recipe {
        private String uri;
    }
}
