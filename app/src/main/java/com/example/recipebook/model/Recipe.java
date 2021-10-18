package com.example.recipebook.model;

import androidx.annotation.NonNull;

public class Recipe {

    private int id, rating;
    private String name, instructions;

    public Recipe(@NonNull int id, @NonNull String name, String instructions, @NonNull int rating) {
        setId(id);
        setRating(rating);
        setName(name);
        setInstructions(instructions);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
