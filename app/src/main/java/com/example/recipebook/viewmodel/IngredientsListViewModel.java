package com.example.recipebook.viewmodel;


import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IngredientsListViewModel extends ViewModel {

    List<String> ingredients;

    public IngredientsListViewModel() {
        ingredients = new ArrayList<>();
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = Arrays.asList(ingredients);
    }

    public String getIngredientsFormatted() {
        StringBuilder sb = new StringBuilder();
        sb.append("LIST OF CURRENT INGREDIENTS\n");
        sb.append("===========================\n");
        for(String ingredient : ingredients) {
            sb.append(ingredient);
            sb.append('\n');
        }
        return sb.toString();
    }
}
