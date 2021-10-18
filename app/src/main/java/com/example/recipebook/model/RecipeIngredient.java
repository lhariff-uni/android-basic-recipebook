package com.example.recipebook.model;

import androidx.annotation.NonNull;

public class RecipeIngredient {

    private int recipeID, ingredientID;

    public RecipeIngredient(@NonNull int recipeID, @NonNull int ingredientID) {
        setRecipeID(recipeID);
        setIngredientID(ingredientID);
    }

    public int getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

    public int getIngredientID() {
        return ingredientID;
    }

    public void setIngredientID(int ingredientID) {
        this.ingredientID = ingredientID;
    }
}
