package com.open_source.worldwide.baking.recipe_details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.open_source.worldwide.baking.Constants;
import com.open_source.worldwide.baking.R;


public class DetailsActivity extends AppCompatActivity
        implements RecipeDetailsFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent receivedIntent = getIntent();
        int recipeId = receivedIntent.getIntExtra(Constants.RECIPE_ID_KEY, -1);

        RecipeDetailsFragment detailsFragment = new RecipeDetailsFragment();

        FragmentManager supportFragmentManager = getSupportFragmentManager();

        Bundle bundle = new Bundle();
        bundle.putInt(Constants.RECIPE_ID_KEY, recipeId);

        detailsFragment.setArguments(bundle);
        supportFragmentManager.beginTransaction().
                add(R.id.recipes_details_container, detailsFragment).commit();

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
