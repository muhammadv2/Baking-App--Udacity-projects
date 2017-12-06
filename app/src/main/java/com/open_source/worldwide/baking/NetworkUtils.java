package com.open_source.worldwide.baking;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.open_source.worldwide.baking.models.Ingredient;
import com.open_source.worldwide.baking.models.Recipe;
import com.open_source.worldwide.baking.models.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class NetworkUtils {


    private NetworkUtils() {
        // creating a private constructor because no one should instantiate this class
    }

    public static ArrayList fetchData(Context context, String dataType, int recipeId) {

        OkHttpClient okHttp = new OkHttpClient();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);
        Request request = new Request.Builder().url(Constants.RECIPES_JSON_URL).build();

        String responseBody = null;
        try {

            Response response = okHttp.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) { //to check if the response returned successfully or not
                responseBody = response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (responseBody == null) return null;

        switch (dataType) {
            case Constants.RECIPE_KEY:
                return getRecipesFromJson(context, responseBody);
            case Constants.STEP_Key:
                return getStepsFromJson(context, responseBody, recipeId);
            case Constants.INGREDIENT_KEY:
                return getRecipeIngredients(context, responseBody, recipeId);
            default:
                return null;
        }
    }

    //extract all the recipes from json file
    private static ArrayList<Recipe> getRecipesFromJson(Context context, String json) {

        Integer id;
        String recipeName;
        Integer recipeServing;
        String recipeImage;

        ArrayList<Recipe> recipes = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(json);
            if (jsonArray.length() > 0) {

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    id = jsonObject.optInt(Constants.ID);
                    recipeName = jsonObject.optString(Constants.RECIPE_NAME);
                    recipeServing = jsonObject.optInt(Constants.RECIPE_SERVING);
                    recipeImage = jsonObject.optString(Constants.RECIPE_IMAGE);


                    recipes.add(new Recipe(id, recipeName, recipeServing, recipeImage));
                }
            }

        } catch (JSONException e) {
            Toast.makeText(context, R.string.error_loading_recipe, Toast.LENGTH_SHORT).show();
        }

        return recipes;

    }

    //extract all ingredient from json file
    private static ArrayList<Ingredient> getRecipeIngredients(Context context, String json, int recipeId) {

        int quantity;
        String measure;
        String ingredient;

        ArrayList<Ingredient> ingredients = new ArrayList<>();

        try {
            JSONArray mainArray = new JSONArray(json);
            JSONObject jsonMainObjects = mainArray.getJSONObject(recipeId);
            JSONArray ingredientsArray = jsonMainObjects.getJSONArray(Constants.INGREDIENTS_ARRAY);
            if (ingredientsArray.length() > 0) {
                for (int i = 0; i < ingredientsArray.length(); i++) {

                    JSONObject ingredientsObject = ingredientsArray.getJSONObject(i);

                    quantity = ingredientsObject.getInt(Constants.INGREDIENTS_QUANTITY);
                    measure = ingredientsObject.getString(Constants.INGREDIENTS_MEASURE);
                    ingredient = ingredientsObject.getString(Constants.INGREDIENTS_INGREDIENT);

                    ingredients.add(new Ingredient(quantity, measure, ingredient));


                }
            }

        } catch (JSONException e) {
            Toast.makeText(context, "error loading json", Toast.LENGTH_SHORT).show();
        }

        return ingredients;

    }

    //extract all steps from json
    private static ArrayList<Step> getStepsFromJson(Context context, String json, int recipeId) {

        int id;
        String shortDescription;
        String description;
        String videoUrl;
        String thumbnailUrl;

        ArrayList<Step> steps = new ArrayList<>();

        try {
            JSONArray mainArray = new JSONArray(json);
            JSONObject jsonMainObjects = mainArray.getJSONObject(recipeId);
            JSONArray stepsArray = jsonMainObjects.getJSONArray(Constants.STEPS_ARRAY);
            if (stepsArray.length() > 0) {
                for (int i = 0; i < stepsArray.length(); i++) {
                    JSONObject jsonObject = stepsArray.getJSONObject(i);

                    id = jsonObject.optInt(Constants.ID);
                    shortDescription = jsonObject.optString(Constants.DETAILS_SHORT_DESCRIPTION);
                    description = jsonObject.optString(Constants.DETAILS_DESCRIPTION);
                    videoUrl = jsonObject.getString(Constants.DETAILS_VIDEO_URL);
                    thumbnailUrl = jsonObject.getString(Constants.DETAILS_THUMBNAIL_URL);


                    steps.add(new Step(id, shortDescription, description, videoUrl, thumbnailUrl));
                }
            }

        } catch (JSONException e) {
            Toast.makeText(context, "error loading json", Toast.LENGTH_SHORT).show();
        }

        return steps;

    }
}
