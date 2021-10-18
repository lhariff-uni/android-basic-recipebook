package com.example.recipebook.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.recipebook.R;
import com.example.recipebook.RecipeAdapter;
import com.example.recipebook.model.Recipe;
import com.example.recipebook.viewmodel.MainViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_RECIPE_REQUEST = 1;
    public static final int EXISTING_RECIPE_REQUEST = 2;

    private MainViewModel viewModel;
    private RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipeAdapter = new RecipeAdapter(this);
        viewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(MainViewModel.class);

        viewModel.getAllRecipes().observe(this, recipes -> {
            recipeAdapter.setData(recipes);
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(recipeAdapter);
        recipeAdapter.setClickListener(new RecipeAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                onExistingRecipeClick(view, position);
            }
        });

        final View view = findViewById(R.id.layout);
        view.post(new Runnable() {
            @Override
            public void run() {
                int totalSpace = view.getHeight();
                int usedSpace = ((LinearLayout) findViewById(R.id.buttonLayout)).getLayoutParams().height;
                recyclerView.getLayoutParams().height = totalSpace - usedSpace;

                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            }
        });
    }

    public void onNewRecipeClick(View v) {
        Intent intent = new Intent(this, NewRecipe.class);
        startActivityForResult(intent, NEW_RECIPE_REQUEST);
    }

    public void onExistingRecipeClick(View v, int position) {
        Intent intent = new Intent(this, ExistingRecipe.class);
        int recipeID = (int) recipeAdapter.getItemId(position);
        Bundle b = viewModel.getRecipeBundle(recipeID);
        intent.putExtras(b);

        startActivityForResult(intent, EXISTING_RECIPE_REQUEST);
    }

    public void onSortClick(View v) {
        List<Recipe> list = null;
        switch(v.getId()) {
            case R.id.ratingSortButton:
                list = viewModel.sortRecipes(false);
                break;
            case R.id.nameSortButton:
            default:
                list = viewModel.sortRecipes(true);
        }
        recipeAdapter.setData(list);
    }

    public void onIngredientsClick(View v) {
        Intent intent = new Intent(this, IngredientsList.class);
        intent.putExtra("ingredients",
                viewModel.ingredientsToString(viewModel.getAllIngredients().getValue()));
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_CANCELED) {
        }
        else {
            if(requestCode == NEW_RECIPE_REQUEST) {
                viewModel.addRecipe(data.getExtras());
            }
            else if(requestCode == EXISTING_RECIPE_REQUEST) {
                int id = data.getIntExtra("id", 0);
                int rating = data.getIntExtra("rating", 1);
                viewModel.editRecipe(id, rating);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        viewModel.getAllRecipes().removeObservers(this);
        viewModel.close();
        super.onDestroy();
    }
}