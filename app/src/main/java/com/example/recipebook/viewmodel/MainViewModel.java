package com.example.recipebook.viewmodel;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.recipebook.Repository;
import com.example.recipebook.model.Ingredient;
import com.example.recipebook.model.Recipe;
import com.example.recipebook.model.RecipeIngredient;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private Repository repository;

    private final LiveData<List<Recipe>> allRecipes;
    private final LiveData<List<Ingredient>> allIngredients;
    private final LiveData<List<RecipeIngredient>> allAssociations;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application.getApplicationContext());

        allRecipes = repository.getAllRecipes();
        allIngredients = repository.getAllIngredients();
        allAssociations = repository.getAllAssociations();
    }

    public void close() {
        repository.close();
    }

    public void addRecipe(Bundle bundle) {
        repository.insert(bundle.getString("name"), bundle.getString("instructions"),
                bundle.getInt("rating"), bundle.getStringArray("ingredients"));
    }

    public void editRecipe(int recipeID, int rating) {
        repository.updateRecipeRating(recipeID, rating);
    }

    public Bundle getRecipeBundle(int recipeID) {
        Recipe recipe = repository.getRecipe(recipeID);
        List<Ingredient> ingredients = repository.getIngredientsFromRecipe(recipeID);
        Bundle b = new Bundle();

        b.putInt("id", recipe.getId());
        b.putString("name", recipe.getName());
        b.putString("instructions", recipe.getInstructions());
        b.putInt("rating", recipe.getRating());
        b.putStringArray("ingredients", ingredientsToString(ingredients));

        return b;
    }

    public List<Recipe> sortRecipes(boolean sortByName) {
        List<Recipe> newList = getAllRecipes().getValue();
        if(sortByName) {
            Collections.sort(newList, new Comparator<Recipe>() {
                @Override
                public int compare(Recipe o1, Recipe o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
        } else {
            Collections.sort(newList, new Comparator<Recipe>() {
                @Override
                public int compare(Recipe o1, Recipe o2) {
                    return String.valueOf(o2.getRating()).compareTo(String.valueOf(o1.getRating()));
                }
            });
        }
        return newList;
    }

    public String[] ingredientsToString(List<Ingredient> ingredients) {
        String[] res = new String[ingredients.size()];
        int i = 0;
        for(Ingredient ingredient : ingredients) {
            res[i++] = ingredient.getIngredientName();
        }
        return res;
    }

    public LiveData<List<RecipeIngredient>> getAllAssociations() {
        return allAssociations;
    }

    public LiveData<List<Recipe>> getAllRecipes() { return allRecipes; }

    public LiveData<List<Ingredient>> getAllIngredients() {
        return allIngredients;
    }

}
