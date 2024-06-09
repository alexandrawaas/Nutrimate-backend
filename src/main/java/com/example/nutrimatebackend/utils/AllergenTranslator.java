package com.example.nutrimatebackend.utils;

public class AllergenTranslator
{
    public static String translateOpenFoodFactsToEdamam(String allergen)
    {
        return switch (allergen)
                {
                    case "milk" -> "dairy-free";
                    case "eggs" -> "egg-free";
                    case "gluten" -> "gluten-free";
                    case "peanuts" -> "peanut-free";
                    case "sesame" -> "sesame-free";
                    case "soy" -> "soy-free";
                    case "shellfish" -> "shellfish-free";
                    case "fish" -> "fish-free";
                    case "tree nuts" -> "tree-nut-free";
                    default -> "";
                };
    }
}
