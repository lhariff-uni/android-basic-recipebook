package com.example.recipebook.model;

import androidx.annotation.NonNull;

public class Ingredient {
    private int id;
    private String ingredientName;

    public Ingredient(@NonNull int id, @NonNull String ingredientName) {
        setId(id);
        setIngredientName(ingredientName);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }
}
