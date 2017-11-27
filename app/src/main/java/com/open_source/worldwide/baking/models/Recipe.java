package com.open_source.worldwide.baking.models;

public class Recipe {

    private static final String TAG = Recipe.class.toString();

    private Integer mRecipeId;
    private String mName;
    private Integer mServings;
    private String mImageUrl;

    public Recipe(int recipeId, String recipeName, Integer recipeServing, String recipeImage) {

        mRecipeId = recipeId;
        mName = recipeName;
        mServings = recipeServing;
        mImageUrl = recipeImage;


    }


    public Integer getRecipeId() {
        return mRecipeId;
    }

    public String getName() {
        return mName;
    }

    public Integer getServings() {
        return mServings;
    }

    public String getImage() {
        return mImageUrl;
    }

}