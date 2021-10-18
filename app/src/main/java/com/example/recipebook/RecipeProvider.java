package com.example.recipebook;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RecipeProvider extends ContentProvider {

    private DatabaseAdapter dbAdapter = null;

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(RecipeContract.AUTHORITY, "recipes", 1);
        uriMatcher.addURI(RecipeContract.AUTHORITY, "recipes/#", 2);
        uriMatcher.addURI(RecipeContract.AUTHORITY, "ingredients", 3);
        uriMatcher.addURI(RecipeContract.AUTHORITY, "ingredients/#", 4);
        uriMatcher.addURI(RecipeContract.AUTHORITY, "recipe_ingredients", 5);
        uriMatcher.addURI(RecipeContract.AUTHORITY, "*", 6);
    }

    @Override
    public boolean onCreate() {
        this.dbAdapter = new DatabaseAdapter(getContext());
        this.dbAdapter.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = dbAdapter.db;

        switch(uriMatcher.match(uri)) {
            case 2:
                selection = "_id = " + uri.getLastPathSegment();
            case 1:
                return db.query("recipes", projection, selection, selectionArgs, null, null, sortOrder);
            case 4:
                selection = "_id = " + uri.getLastPathSegment();
            case 3:
                return db.query("ingredients", projection, selection, selectionArgs, null, null, sortOrder);
            case 5:
                String q5 = "SELECT recipe_id, ingredient_id FROM recipe_ingredients";
                return db.rawQuery(q5, selectionArgs);
            case 6:
                String q6 = "SELECT * FROM recipes UNION SELECT * FROM ingredients UNION SELECT * FROM recipe_ingredients";
                return db.rawQuery(q6, selectionArgs);
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        String contentType;

        if (uri.getLastPathSegment()==null) {
            contentType = RecipeContract.CONTENT_TYPE_MULTIPLE;
        } else {
            contentType = RecipeContract.CONTENT_TYPE_SINGLE;
        }

        return contentType;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = dbAdapter.db;
        String tableName;

        switch(uriMatcher.match(uri)) {
            case 1:
                tableName = "recipes";
                break;
            case 3:
                tableName = "ingredients";
                break;
            case 5:
                tableName = "recipe_ingredients";
                break;
            default:
                tableName = "recipes";
                break;
        }

        long id = db.insert(tableName, null, values);
        db.close();
        Uri nu = ContentUris.withAppendedId(uri, id);

        getContext().getContentResolver().notifyChange(nu, null);

        return nu;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException("not implemented");
    }
}
