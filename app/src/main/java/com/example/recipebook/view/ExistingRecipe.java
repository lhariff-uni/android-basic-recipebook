package com.example.recipebook.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.recipebook.R;
import com.example.recipebook.viewmodel.ExistingRecipeViewModel;

public class ExistingRecipe extends AppCompatActivity {

    private ExistingRecipeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);

        viewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(ExistingRecipeViewModel.class);
        viewModel.setBundle(getIntent().getExtras());

        EditText nameText = findViewById(R.id.recipeName);
        EditText instructionText = findViewById(R.id.recipeInstructions);
        EditText ingredientText = findViewById(R.id.recipeIngredients);
        RatingBar ratingBar = findViewById(R.id.recipeRatingBar);

        nameText.setText(viewModel.getRecipeName());
        instructionText.setText(viewModel.getRecipeInstructions());
        ingredientText.setText(viewModel.getRecipeIngredients());
        ratingBar.setRating(viewModel.getRecipeRating());

        //  Customise UI components
        nameText.setEnabled(false);
        instructionText.setEnabled(false);
        ingredientText.setEnabled(false);
        nameText.setTextColor(Color.BLACK);
        instructionText.setTextColor(Color.BLACK);
        ingredientText.setTextColor(Color.BLACK);

        //  Programmatically set onClick
        Button button = findViewById(R.id.recipeEnterButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEnterClick(v);
            }
        });
    }

    public void onEnterClick(View v) {
        RatingBar ratingBar = findViewById(R.id.recipeRatingBar);
        Intent intent = new Intent();
        viewModel.setRecipeRating((int) ratingBar.getRating());
        intent.putExtras(viewModel.getBundle());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
