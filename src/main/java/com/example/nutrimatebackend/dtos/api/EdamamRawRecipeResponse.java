package com.example.nutrimatebackend.dtos.api;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class EdamamRawRecipeResponse {
    private int from;
    private int to;
    private int count;
    private Links _links;
    private List<Hit> hits;

    @Data
    public static class Links {
        private Link self;
        private Link next;
    }

    @Data
    public static class Link {
        private String href;
        private String title;
    }

    @Data
    public static class Hit {
        private Recipe recipe;
        private Links _links;
    }

    public static class Recipe {
        private String uri;
        private String label;
        private String image;
        private Images images;
        private String source;
        private String url;
        private String shareAs;
        private int yield;
        private List<String> dietLabels;
        private List<String> healthLabels;
        private List<String> cautions;
        private List<String> ingredientLines;
        private List<Ingredient> ingredients;
        private double calories;
        private double glycemicIndex;
        private double inflammatoryIndex;
        private double totalCO2Emissions;
        private String co2EmissionsClass;
        private double totalWeight;
        private List<String> cuisineType;
        private List<String> mealType;
        private List<String> dishType;
        private List<String> instructions;
        private List<String> tags;
        private String externalId;
        private Map<String, Nutrient> totalNutrients;
        private Map<String, Nutrient> totalDaily;
        private List<Digest> digest;

        public static class Images {
            private Image THUMBNAIL;
            private Image SMALL;
            private Image REGULAR;
            private Image LARGE;
        }

        public static class Image {
            private String url;
            private int width;
            private int height;
        }

        public static class Ingredient {
            private String text;
            private double quantity;
            private String measure;
            private String food;
            private double weight;
            private String foodId;
        }

        public static class Nutrient {
            private String label;
            private double quantity;
            private String unit;
        }

        public static class Digest {
            private String label;
            private String tag;
            private String schemaOrgTag;
            private double total;
            private boolean hasRDI;
            private double daily;
            private String unit;
            private String sub;
        }
    }
}
