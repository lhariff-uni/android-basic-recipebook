package com.example.recipebook;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.recipebook.model.Ingredient;
import com.example.recipebook.model.Recipe;
import com.example.recipebook.model.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Repository {

    private DatabaseAdapter dbAdapter;
    private MutableLiveData<List<Recipe>> allRecipes;
    private MutableLiveData<List<Ingredient>> allIngredients;
    private MutableLiveData<List<RecipeIngredient>> allAssociations;

    public Repository(Context context) {
        dbAdapter = new DatabaseAdapter(context);
        dbAdapter.open();

        allRecipes = new MutableLiveData<>();
        allIngredients = new MutableLiveData<>();
        allAssociations = new MutableLiveData<>();

        allRecipes.setValue(dbAdapter.fetchRecipes());
        allIngredients.setValue(dbAdapter.fetchIngredients());
        allAssociations.setValue(dbAdapter.fetchAssociations());
    }

    public void close() {
        dbAdapter.close();
    }

    public void insert(@NonNull String name, String instructions,
                       @NonNull int rating, String[] ingredients) {
        ReturnAllTypes results = dbAdapter.addRecipe(name, instructions, rating, ingredients);

        List<Recipe> newRecipes = allRecipes.getValue();
        newRecipes.add(results.recipe);
        allRecipes.postValue(newRecipes);

        List<Ingredient> newIngredients = allIngredients.getValue();
        newIngredients.addAll(results.ingredients);
        allIngredients.postValue(newIngredients);

        List<RecipeIngredient> newAssociations = allAssociations.getValue();
        newAssociations.addAll(results.recipeIngredients);
        allAssociations.postValue(newAssociations);
    }

    public void updateRecipeRating(int recipeID, int rating) {
        List<Recipe> newList = allRecipes.getValue();
        for(int i = 0; i < newList.size(); i++) {
            if(newList.get(i).getId() == recipeID) {
                newList.get(i).setRating(rating);
                break;
            }
        }
        allRecipes.postValue(newList);
        dbAdapter.updateRecipeRating(recipeID, rating);
    }

    public Recipe getRecipe(int recipeID) {
        Recipe recipe = null;
        for(int i = 0; i < allRecipes.getValue().size(); i++) {
            if(allRecipes.getValue().get(i).getId() == recipeID) {
                recipe = allRecipes.getValue().get(i);
            }
        }
        return recipe;
    }

    public LiveData<List<RecipeIngredient>> getAllAssociations() {
        return allAssociations;
    }

    public LiveData<List<Ingredient>> getAllIngredients() {
        return allIngredients;
    }

    public LiveData<List<Recipe>> getAllRecipes() {
        return allRecipes;
    }

    public List<Ingredient> getIngredientsFromRecipe(int recipeID) {
        List<Ingredient> res = new ArrayList<>();
        List<Integer> ids = new ArrayList<>();
        for(RecipeIngredient ri : allAssociations.getValue()) {
            if(ri.getRecipeID() == recipeID) {
                ids.add(ri.getIngredientID());
            }
        }
        for(int i : ids) {
            for(Ingredient ingredient : allIngredients.getValue()) {
                if(i == ingredient.getId()) {
                    res.add(ingredient);
                    break;
                }
            }
        }

        return res;
    }
}

