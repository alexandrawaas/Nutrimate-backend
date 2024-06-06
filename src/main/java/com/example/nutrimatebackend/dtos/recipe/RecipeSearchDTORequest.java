package com.example.nutrimatebackend.dtos.recipe;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RecipeSearchDTORequest
{
    @JsonProperty("categories") public List<String> categories;
    @JsonProperty("allergens") public List<String> allergens;
}
