package com.open_source.worldwide.baking.models;

public final class Recipe {

    private final int mRecipeId;
    private final String mName;
    private final int mServings;
    private final String mImageUrl;

    public Recipe(int recipeId, String recipeName, int recipeServing, String recipeImage) {

        mRecipeId = recipeId;
        mName = recipeName;
        mServings = recipeServing;
        mImageUrl = recipeImage;

    }

    public String getName() {
        return mName;
    }

    public int getServings() {
        return mServings;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }
}