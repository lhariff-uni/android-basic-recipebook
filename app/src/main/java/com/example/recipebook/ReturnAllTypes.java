package com.example.recipebook;

import com.example.recipebook.model.Ingredient;
import com.example.recipebook.model.Recipe;
import com.example.recipebook.model.RecipeIngredient;

import java.util.List;

public class ReturnAllTypes {
    public final Recipe recipe;
    public final List<Ingredient> ingredients;
    public final List<RecipeIngredient> recipeIngredients;

    public ReturnAllTypes(Recipe recipe, List<Ingredient> ingredients,
                          List<RecipeIngredient> recipeIngredients) {
        this.recipe = recipe;
        this.ingredients = ingredients;
        this.recipeIngredients = recipeIngredients;
    }
}
