package com.example.recipebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

public class DatabaseItemView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_item_view);

        Bundle bundle = getIntent().getExtras();

        TextView recipeName = findViewById(R.id.dbRecipeName);
        recipeName.setText(bundle.getString("name"));

        RatingBar ratingBar = findViewById(R.id.dbRatingBar);
        ratingBar.setRating(bundle.getFloat("rating"));
    }
}