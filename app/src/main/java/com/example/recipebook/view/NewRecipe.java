package com.example.recipebook.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.recipebook.R;
import com.example.recipebook.viewmodel.NewRecipeViewModel;


public class NewRecipe extends AppCompatActivity {

    private NewRecipeViewModel viewModel;

    EditText nameText;
    EditText instructionText;
    EditText ingredientText;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);

        viewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(NewRecipeViewModel.class);

        nameText = findViewById(R.id.recipeName);
        instructionText = findViewById(R.id.recipeInstructions);
        ingredientText = findViewById(R.id.recipeIngredients);
        ratingBar = findViewById(R.id.recipeRatingBar);

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
        Bundle bundle = viewModel.createBundle(String.valueOf(nameText.getText()),
                String.valueOf(instructionText.getText()),
                String.valueOf(ingredientText.getText()),
                (int) ratingBar.getRating());

        if(bundle != null) {
            Intent intent = new Intent();
            intent.putExtras(bundle);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }

}