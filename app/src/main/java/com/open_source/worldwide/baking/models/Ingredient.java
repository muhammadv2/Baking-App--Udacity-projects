package com.open_source.worldwide.baking.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import com.open_source.worldwide.baking.R;

public final class Ingredient implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @NonNull
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    private final Integer mQuantity;
    private final String mMeasure;

    private final String mIngredient;

    public Ingredient(Integer quantity, String measure, String ingredient) {

        mQuantity = quantity;
        mMeasure = measure;
        mIngredient = ingredient;
    }

    public Integer getQuantity() {
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

    private Ingredient(Parcel in) {
        mQuantity = in.readInt();
        mMeasure = in.readString();
        mIngredient = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mQuantity);
        dest.writeString(mMeasure);
        dest.writeString(mIngredient);
    }
}
