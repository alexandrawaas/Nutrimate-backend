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
                    case "soybeans" -> "soy-free";
                    case "shellfish" -> "shellfish-free";
                    case "fish" -> "fish-free";
                    case "nuts" -> "tree-nut-free";
                    case "mustard" -> "mustard-free";
                    case "sulphur dioxide and sulphites" -> "sulfite-free";
                    case "lupin" -> "lupine-free";
                    case "molluscs" -> "mollusk-free";
                    case "celery" -> "celery-free";
                    case "crustaceans" -> "crustacean-free";
                    default -> "";
                };
    }
}
