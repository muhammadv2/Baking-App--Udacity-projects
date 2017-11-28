package com.open_source.worldwide.baking.recipe_details;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.open_source.worldwide.baking.Constants;
import com.open_source.worldwide.baking.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailsActivity extends AppCompatActivity {

    public static final String SHOW_DETAILS_ACTION = "details";
    public static final String SHOW_STEP_DETAILS_ACTION = "step";

    @BindView(R.id.view_pager)
    ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getSupportActionBar().setHomeButtonEnabled(true);

        Intent receivedIntent = getIntent();
        ButterKnife.bind(this);

        if (receivedIntent.getAction() == (SHOW_DETAILS_ACTION)) {

            int recipeId = receivedIntent.getIntExtra(Constants.RECIPE_ID_KEY, -1);

            RecipeDetailsPagerAdapter adapter =
                    new RecipeDetailsPagerAdapter(getSupportFragmentManager(), recipeId);
            mViewPager.setAdapter(adapter);

        } else {

            mViewPager.setVisibility(View.GONE);

            StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
            FragmentManager supportFragmentManager = getSupportFragmentManager();
            supportFragmentManager.beginTransaction()
                    .add(R.id.step_details_container, stepDetailsFragment).commit();
        }

    }

}
