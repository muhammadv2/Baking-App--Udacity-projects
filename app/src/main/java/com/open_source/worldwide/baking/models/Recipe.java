package com.open_source.worldwide.baking.models;

public final class Recipe {

    private int mRecipeId;
    private String mName;
    private int mServings;
    private String mImageUrl;

    public Recipe(int recipeId, String recipeName, int recipeServing, String recipeImage) {

        mRecipeId = recipeId;
        mName = recipeName;
        mServings = recipeServing;
        mImageUrl = recipeImage;


    }


    public int getRecipeId() {
        return mRecipeId;
    }

    public String getName() {
        return mName;
    }

    public int getServings() {
        return mServings;
    }

    public String getImage() {
        return mImageUrl;
    }

}