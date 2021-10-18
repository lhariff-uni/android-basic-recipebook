package com.example.recipebook.viewmodel;

import android.app.Application;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class NewRecipeViewModel extends AndroidViewModel {
    public NewRecipeViewModel(@NonNull Application application) {
        super(application);
    }

    public Bundle createBundle(String name, String instruction,
                               String ingredient, int rating) {

        //  Toast to indicate empty fields
        if(hasEmptyFields(name, instruction, ingredient, rating)) {
            Toast.makeText(getApplication(), "Please fill all fields.",
                    Toast.LENGTH_LONG).show();
            return null;
        }
        String[] ingredientList = ingredient.split("\\r?\\n");
        Bundle b = new Bundle();
        b.putString("name", name);
        b.putString("instructions", instruction);
        b.putInt("rating", rating);
        b.putStringArray("ingredients", ingredientList);
        return b;
    }

    private boolean hasEmptyFields(String nameText, String instructionText,
                                   String ingredientText, int ratingNum) {
        boolean name = nameText.length() == 0;
        boolean instruction = instructionText.length() == 0;
        boolean ingredient = ingredientText.length() == 0;
        boolean rating = ratingNum == 0;

        return name || instruction || ingredient || rating;
    }
}
