package com.example.recipebook.viewmodel;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

public class ExistingRecipeViewModel extends ViewModel {
    Bundle bundle;

    public ExistingRecipeViewModel() {
        this.bundle = new Bundle();
    }

    public int getRecipeID() {
        return bundle.getInt("id");
    }

    public String getRecipeName() {
        return bundle.getString("name");
    }

    public String getRecipeInstructions() {
        return bundle.getString("instructions");
    }

    public String getRecipeIngredients() {
        StringBuilder sb = new StringBuilder();
        for(String ingredient : bundle.getStringArray("ingredients")) {
            sb.append(ingredient);
            sb.append("\n");
        }
        return sb.toString();
    }

    public int getRecipeRating() {
        return bundle.getInt("rating");
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public void setRecipeRating(int rating) {
        bundle.remove("rating");
        bundle.putInt("rating", rating);
    }
}
