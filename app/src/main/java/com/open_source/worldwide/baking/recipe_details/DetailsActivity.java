package com.open_source.worldwide.baking.recipe_details;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.open_source.worldwide.baking.Constants;
import com.open_source.worldwide.baking.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailsActivity extends AppCompatActivity {

    public static final String SHOW_DETAILS_ACTION = "details";

    @BindView(R.id.view_pager)
    ViewPager mViewPager;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        final Intent receivedIntent = getIntent();
        ButterKnife.bind(this);

        String title = receivedIntent.getStringExtra(Constants.RECIPE_NAME);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(title);

        if (receivedIntent.getAction() == (SHOW_DETAILS_ACTION)) {
            Log.i(SHOW_DETAILS_ACTION, "onCreate: if invoked");

            int recipeId = receivedIntent.getIntExtra(Constants.RECIPE_ID_KEY, -1);

            RecipeDetailsPagerAdapter adapter =
                    new RecipeDetailsPagerAdapter(getSupportFragmentManager(), recipeId);
            mViewPager.setAdapter(adapter);

//            if (getResources().getBoolean(R.bool.isTablet)) {
//
//                mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
//                    @Override
//                    public void onPageSelected(int position) {
//
//                        if (position == 1) {
//                            StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
//
//
//                            Bundle bundle = new Bundle();
//                            bundle.putInt(Constants.STEP_ID_KEY,
//                                    receivedIntent.getIntExtra(Constants.STEP_ID_KEY, 0));
//                            bundle.putInt(Constants.RECIPE_ID_KEY,
//                                    receivedIntent.getIntExtra(Constants.RECIPE_ID_KEY, 0));
//
//                            stepDetailsFragment.setArguments(bundle);
//
//                        }
//                    }
//                });
//            }


        } else {

            mViewPager.setVisibility(View.GONE);
            Log.i(SHOW_DETAILS_ACTION, "onCreate: else invoked");

            StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();


            Bundle bundle = new Bundle();
            bundle.putInt(Constants.STEP_ID_KEY,
                    receivedIntent.getIntExtra(Constants.STEP_ID_KEY, -1));
            bundle.putInt(Constants.RECIPE_ID_KEY,
                    receivedIntent.getIntExtra(Constants.RECIPE_ID_KEY, -1));

            stepDetailsFragment.setArguments(bundle);

            FragmentManager supportFragmentManager = getSupportFragmentManager();
            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                        .add(R.id.step_details_container, stepDetailsFragment).commit();
            }
        }

    }
}
