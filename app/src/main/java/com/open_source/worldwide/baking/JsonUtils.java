package com.open_source.worldwide.baking;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.open_source.worldwide.baking.models.Ingredient;
import com.open_source.worldwide.baking.models.Recipe;
import com.open_source.worldwide.baking.models.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class JsonUtils {

    private static final String TAG = JsonUtils.class.toString();

    public static ArrayList<Recipe> getRecipesFromJson(Context context) {

        Integer id;
        String recipeName;
        Integer recipeServing;
        String recipeImage;

        ArrayList<Recipe> recipes = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(loadJSONFromAsset(context));

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                id = jsonObject.optInt(Constants.ID);
                recipeName = jsonObject.optString(Constants.RECIPE_NAME);
                recipeServing = jsonObject.optInt(Constants.RECIPE_SERVING);
                recipeImage = jsonObject.optString(Constants.RECIPE_IMAGE);

                recipes.add(new Recipe(id, recipeName, recipeServing, recipeImage));
            }

        } catch (JSONException e) {
            Toast.makeText(context, R.string.error_loading_recipe, Toast.LENGTH_SHORT).show();
        }

        return recipes;

    }

    public static ArrayList<Ingredient> getRecipeIngredients(Context context, int recipeId) {

        int quantity;
        String measure;
        String ingredient;

        ArrayList<Ingredient> ingredients = new ArrayList<>();

        try {
            JSONArray mainArray = new JSONArray(loadJSONFromAsset(context));
            JSONObject jsonMainObjects = mainArray.getJSONObject(recipeId);
            JSONArray ingredientsArray = jsonMainObjects.getJSONArray(Constants.INGREDIENTS_ARRAY);
            for (int i = 0; i < ingredientsArray.length(); i++) {

                JSONObject ingredientsObject = ingredientsArray.getJSONObject(i);

                quantity = ingredientsObject.getInt(Constants.INGREDIENTS_QUANTITY);
                measure = ingredientsObject.getString(Constants.INGREDIENTS_MEASURE);
                ingredient = ingredientsObject.getString(Constants.INGREDIENTS_INGREDIENT);

                Log.i(TAG, "getRecipeIngredients: " + quantity + "  " + measure);
                ingredients.add(new Ingredient(quantity, measure, ingredient));


            }

        } catch (JSONException e) {
            Toast.makeText(context, "error loading json", Toast.LENGTH_SHORT).show();
        }

        return ingredients;

    }


    public static ArrayList<Step> getStepsFromJson(Context context, int recipeId) {

        int id;
        String shortDescription;
        String description;
        String videoUrl;
        String thumbnailUrl;

        ArrayList<Step> steps = new ArrayList<>();

        try {
            JSONArray mainArray = new JSONArray(loadJSONFromAsset(context));
            JSONObject jsonMainObjects = mainArray.getJSONObject(recipeId);
            JSONArray stepsArray = jsonMainObjects.getJSONArray(Constants.STEPS_ARRAY);
            for (int i = 0; i < stepsArray.length(); i++) {
                JSONObject jsonObject = stepsArray.getJSONObject(i);

                id = jsonObject.optInt(Constants.ID);
                shortDescription = jsonObject.optString(Constants.DETAILS_SHORT_DESCRIPTION);
                description = jsonObject.optString(Constants.DETAILS_DESCRIPTION);
                videoUrl = jsonObject.getString(Constants.DETAILS_VIDEO_URL);
                thumbnailUrl = jsonObject.getString(Constants.DETAILS_THUMBNAIL_URL);

                Log.i(TAG, "getRecipesFromJson: " + shortDescription + "" + id);

                steps.add(new Step(id, shortDescription, description, videoUrl, thumbnailUrl));

            }

        } catch (JSONException e) {
            Toast.makeText(context, "error loading json", Toast.LENGTH_SHORT).show();
        }

        return steps;

    }

    @Nullable
    private static String loadJSONFromAsset(Context context) {

        String json;
        try {
            InputStream is = context.getAssets().open("Recipes.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            Toast.makeText(context, R.string.error_loading_recipe, Toast.LENGTH_LONG)
                    .show();
            return null;
        }
        return json;

    }
}
