package com.example.recipebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.recipebook.model.Ingredient;
import com.example.recipebook.model.Recipe;
import com.example.recipebook.model.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAdapter {

    private static final String INGREDIENTS_TABLE = "ingredients";
    private static final String RECIPES_TABLE = "recipes";
    private static final String ASSOC_TABLE = "recipe_ingredients";

    private DatabaseHelper dbHelper;
    public SQLiteDatabase db;
    private Context context;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, "recipeDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE if not exists " + RECIPES_TABLE +
                    "(_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    "name VARCHAR(128) NOT NULL, " +
                    "instructions VARCHAR(128) NOT NULL, " +
                    "rating INTEGER);");

            db.execSQL("CREATE TABLE if not exists " + INGREDIENTS_TABLE +
                    "(_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "ingredientname VARCHAR(128) NOT NULL UNIQUE);");

            db.execSQL("CREATE TABLE if not exists " + ASSOC_TABLE +
                    "(recipe_id INT NOT NULL," +
                    " ingredient_id INT NOT NULL," +
                    " CONSTRAINT fk1 FOREIGN KEY (recipe_id) REFERENCES recipes (_id)," +
                    " CONSTRAINT fk2 FOREIGN KEY (ingredient_id) REFERENCES ingredients (_id)," +
                    " CONSTRAINT _id PRIMARY KEY (recipe_id, ingredient_id) );");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + RECIPES_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + INGREDIENTS_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + ASSOC_TABLE);
            onCreate(db);
        }
    }

    public DatabaseAdapter(Context context) {
        this.context = context;
    }

    public DatabaseAdapter open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    public ReturnAllTypes addRecipe(String name, String instructions, int rating, String[] ingredients) {
        Cursor c;

        //  Inserting into recipes table
        db.execSQL("INSERT INTO " + RECIPES_TABLE + "(name, instructions, rating) " +
                "VALUES " +
                "('" + name.toUpperCase() +
                "','" + instructions.toUpperCase() +
                "','" + rating + "');");

        //  Find last inserted id
        c = db.rawQuery("SELECT last_insert_rowid() FROM recipes", null);
        c.moveToFirst();
        int recipeID = c.getInt(0);
        c.close();

        Recipe recipe = new Recipe(recipeID, name.toUpperCase(),
                instructions.toUpperCase(), rating);

        List<Ingredient> ingredientList = new ArrayList<>();
        List<RecipeIngredient> recipeIngredientList = new ArrayList<>();
        for(String ingredientName : ingredients) {
            //  Inserting into ingredients table <- holds value as UNIQUE
            try {
                    db.execSQL("INSERT INTO " + INGREDIENTS_TABLE + "(ingredientname) " +
                            "VALUES " +
                            "('" + ingredientName.toUpperCase() + "');");
            } catch(SQLException e) {
                e.printStackTrace();
            }

            //  Find ID of current ingredient
            c = db.rawQuery("SELECT _id FROM ingredients WHERE ingredientname = ?",
                    new String[] {ingredientName.toUpperCase()});
            c.moveToFirst();
            int ingredientID = c.getInt(0);
            c.close();

            Ingredient ingredient = new Ingredient(ingredientID, ingredientName.toUpperCase());
            ingredientList.add(ingredient);

            //  Inserting into association table, using recipeID and ingredientID
            db.execSQL("INSERT INTO " + ASSOC_TABLE + "(recipe_id, ingredient_id) " +
                    "VALUES " +
                    "('" + recipeID + "','" + ingredientID + "');");
            recipeIngredientList.add(new RecipeIngredient(recipeID, ingredientID));
        }

        return new ReturnAllTypes(recipe, ingredientList, recipeIngredientList);
    }

    public void updateRecipeRating(int recipeID, int rating) {
        ContentValues cv = new ContentValues();
        cv.put("rating",String.valueOf(rating));

        db.update("recipes", cv, "_id = ?",
                new String[]{String.valueOf(recipeID)});
    }

    public List<Ingredient> fetchIngredients() {
        Cursor c = db.query(INGREDIENTS_TABLE, new String[] { "_id", "ingredientname" },
                null, null, null, null, null);
        c.moveToFirst();
        List<Ingredient> ingredients = new ArrayList<>();
        do {
            ingredients.add(new Ingredient(c.getInt(0), c.getString(1)));
        } while(c.moveToNext());
        return ingredients;
    }
    public List<Recipe> fetchRecipes() {
        Cursor c = db.query(RECIPES_TABLE, new String[] { "_id", "name", "instructions", "rating" },
                null, null, null, null, null);
        c.moveToFirst();
        List<Recipe> recipes = new ArrayList<>();
        do {
            recipes.add(new Recipe(c.getInt(0), c.getString(1),
                    c.getString(2), c.getInt(3)));
        } while(c.moveToNext());
        return recipes;
    }
    public List<RecipeIngredient> fetchAssociations() {
        Cursor c = db.query(ASSOC_TABLE, new String[] { "recipe_id", "ingredient_id" },
                null, null, null, null, null);
        c.moveToFirst();
        List<RecipeIngredient> recipeIngredients = new ArrayList<>();
        do {
            recipeIngredients.add(new RecipeIngredient(c.getInt(0),
                    c.getInt(1)));
        } while(c.moveToNext());
        return recipeIngredients;
    }

    public Cursor fetchAll() {
        Cursor c = db.query("myList", new String[] { "_id", "name", "colour" },
                null, null, null, null, null);
        return c;
    }

}
