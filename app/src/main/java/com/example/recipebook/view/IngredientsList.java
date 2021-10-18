package com.example.recipebook.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.recipebook.R;
import com.example.recipebook.viewmodel.IngredientsListViewModel;

public class IngredientsList extends AppCompatActivity {

    private IngredientsListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_list);

        viewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(IngredientsListViewModel.class);

        viewModel.setIngredients(getIntent().getStringArrayExtra("ingredients"));

        EditText listText = findViewById(R.id.ingredientsListText);
        listText.setText(viewModel.getIngredientsFormatted());
        listText.setEnabled(false);
        listText.setTextColor(Color.BLACK);
    }

    public void onBackClick(View v) {
        finish();
    }
}