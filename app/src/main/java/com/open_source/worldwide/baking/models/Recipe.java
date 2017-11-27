package com.open_source.worldwide.baking.models;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSourceInputStream;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.google.android.exoplayer2.util.Util;
import com.open_source.worldwide.baking.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Recipe {

    private final String TAG = Recipe.class.toString();

    private Integer recipeId;
    private String name;
    private Integer servings;
    private String image;

    public Recipe(int id, String recipeName, Integer recipeServing, String recipeImage) {
    }


    /**
     * Gets and ArrayList of the IDs for all of the Recipes from the JSON file.
     */
    public static Recipe getRecipeByID(Context context, int RecipeID) {
        JsonReader reader;
        try {
            reader = readJSONFile(context);
            reader.beginArray();
            while (reader.hasNext()) {
                Recipe currentRecipe = readEntry(reader);
                if (currentRecipe.getRecipeId() == RecipeID) {
                    reader.close();
                    return currentRecipe;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    @NonNull
    private static Recipe readEntry(JsonReader reader) {
        Integer id = -1;
        String recipeName = null;
        Integer recipeServing = null;
        String recipeImage = null;

        final String ID = "id";
        final String RECIPE_NAME = "name";
        final String RECIPE_SERVING = "servings";
        final String RECIPE_IMAGE = "image";
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();

                switch (name) {

                    case ID:
                        id = reader.nextInt();
                        break;
                    case RECIPE_NAME:
                        recipeName = reader.nextString();
                        break;
                    case RECIPE_SERVING:
                        recipeServing = reader.nextInt();
                        break;
                    case RECIPE_IMAGE:
                        recipeImage = reader.nextString();
                        break;
                    default:
                        break;
                }
            }
            reader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i(Recipe.class.toString(), "readEntry: id " + id + recipeName);


        return new Recipe(id, recipeName, recipeServing, recipeImage);
    }

    public String loadJSONFromAsset(Context context) {

        String json = null;
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

    try{
        JSONObject obj = new JSONObject(loadJSONFromAsset());
        JSONArray m_jArry = obj.getJSONArray("formules");

        for (int i = 0; i < m_jArry.length(); i++) {
            JSONObject jo_inside = m_jArry.getJSONObject(i);
            Log.d("Details-->", jo_inside.getString("formule"));
            String formula_value = jo_inside.getString("formule");
            String url_value = jo_inside.getString("url");

            //Add your values in your `ArrayList` as below:


        }
    } catch(
    JSONException e)

    {
        Toast.makeText(context, R.string.error_loading_recipe, Toast.LENGTH_LONG)
                .show();
    }

}
    /**
     * Method for creating a JsonReader object that points to the JSON array of Recipes.
     */
    private static JsonReader readJSONFile(Context context) throws IOException {
        AssetManager assetManager = context.getAssets();
        String uri = null;

        try {
            for (String asset : assetManager.list("")) {
                if (asset.endsWith("Recipes.json")) {
                    uri = "asset:///" + asset;
                }
            }
        } catch (IOException e) {
            Toast.makeText(context, R.string.error_loading_recipe, Toast.LENGTH_LONG)
                    .show();
        }

        String userAgent = Util.getUserAgent(context, "Baking");
        DataSource dataSource = new DefaultDataSource(context, null, userAgent, false);
        DataSpec dataSpec = new DataSpec(Uri.parse(uri));
        InputStream inputStream = new DataSourceInputStream(dataSource, dataSpec);

        JsonReader reader;
        try {
            reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        } finally {
            Util.closeQuietly(dataSource);
        }

        return reader;

    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public String getName() {
        return name;
    }

    public Integer getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

}