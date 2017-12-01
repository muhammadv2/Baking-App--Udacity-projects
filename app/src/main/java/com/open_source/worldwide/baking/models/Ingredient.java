package com.open_source.worldwide.baking.models;

import com.open_source.worldwide.baking.R;

public final class Ingredient {

    private final int mQuantity;
    private final String mMeasure;
    private final String mIngredient;

    public Ingredient(int quantity, String measure, String ingredient) {

        mQuantity = quantity;
        mMeasure = measure;
        mIngredient = ingredient;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public String getMeasure() {
        return mMeasure;
    }

    public String getIngredient() {
        return mIngredient;
    }

    public static int setCorrectImage(String key) {

        switch (key) {
            case "CUP":
                return R.drawable.cup_ing;
            case "TBLSP":
                return R.drawable.spoon_ingr;
            case "TSP":
                return R.drawable.spoon_ingr;
            case "K":
                return R.drawable.kg_ing;
            case "G":
                return R.drawable.gram_ing;
            default:
                return R.drawable.default_ingr;

        }
    }
}
