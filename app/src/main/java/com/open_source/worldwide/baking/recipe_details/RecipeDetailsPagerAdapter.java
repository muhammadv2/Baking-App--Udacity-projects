package com.open_source.worldwide.baking.recipe_details;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.open_source.worldwide.baking.Constants;


public class RecipeDetailsPagerAdapter extends FragmentPagerAdapter {

    private static int NUM_ITEMS = 2;

    private static int mRecipeId;

    public RecipeDetailsPagerAdapter(FragmentManager fm, int recipeId) {
        super(fm);
        mRecipeId = recipeId;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return RecipeDetailsFragment.newInstance(0, mRecipeId);
            case 1:
                return RecipeDetailsFragment.newInstance(1, mRecipeId);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return Constants.INGREDIENTS_PAGE_NAME;
            case 1:
                return Constants.STEPS_PAGE_NAME;
            default:
                return null;
        }

    }
}
