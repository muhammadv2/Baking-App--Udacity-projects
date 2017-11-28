package com.open_source.worldwide.baking.recipes_main;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.open_source.worldwide.baking.R;

public  class RecipesActivity extends AppCompatActivity
        implements RecipesFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
