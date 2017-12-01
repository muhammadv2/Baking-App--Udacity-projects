package com.open_source.worldwide.baking.recipe_details;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.open_source.worldwide.baking.Adapters.RecipeDetailsPagerAdapter;
import com.open_source.worldwide.baking.Constants;
import com.open_source.worldwide.baking.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailsActivity extends AppCompatActivity {

    //Action to distinguish between intents coming from RecipesFragment and RecipesDetailsFragment
    public static final String SHOW_DETAILS_ACTION = "details";

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //save a reference of the coming intent
        final Intent receivedIntent = getIntent();

        //binding butterKnife
        ButterKnife.bind(this);

        //getting the recipe name from the intent to set it on the ActionBar
        String title = receivedIntent.getStringExtra(Constants.RECIPE_NAME);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(title);

        //get the recipeId key
        int recipeId = receivedIntent.getIntExtra(Constants.RECIPE_ID_KEY, -1);

        //get a reference of the RecipeDetailsPagerAdapter passing the recipe id to help the adapter
        //set the right content over the views
        final RecipeDetailsPagerAdapter adapter =
                new RecipeDetailsPagerAdapter(getSupportFragmentManager(), recipeId);


        //getting a reference of FrameLayout that will hold the fragment
        final FrameLayout frameLayout = findViewById(R.id.step_details_container);

        //check if the device is a tablet and in landscape mode
        if (isTabletAndLandOrientation()) {
            //set the adapter on the view pager
            mViewPager.setAdapter(adapter);

            //listen to the page selected on view pager if steps page show the content of the step
            //if not hide it
            mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {

                    if (position == 1) {
                        if (isTabletAndLandOrientation()) {
                            frameLayout.setVisibility(View.VISIBLE);
                        }
                    } else {
                        frameLayout.setVisibility(View.GONE);
                    }
                }
            });
        } else {

            //check if the intent has the action of show details action
            if (receivedIntent.getAction() == SHOW_DETAILS_ACTION) {
                //set the adapter on the view pager
                mViewPager.setAdapter(adapter);

            } else {
                //if not set the visibility view pager to gone and show the details fragment
                mViewPager.setVisibility(View.GONE);
                Log.i("DetailsActivity", "onCreate: ");
                showStepDetailsFragment(savedInstanceState, receivedIntent);
            }
        }
    }

    /**
     * Method using the intent coming from RecipeDetailsFragment to populate the details fragment
     * with its data and using the SupportFragmentManager to set the fragment
     */
    private void showStepDetailsFragment(Bundle savedInstanceState, Intent receivedIntent) {
        StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(Constants.STEP_ID_KEY,
                receivedIntent.getIntExtra(Constants.STEP_ID_KEY, 0));
        bundle.putInt(Constants.RECIPE_ID_KEY,
                receivedIntent.getIntExtra(Constants.RECIPE_ID_KEY, 0));

        stepDetailsFragment.setArguments(bundle);

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.step_details_container, stepDetailsFragment).commit();
        }
    }

    /**
     * @return a boolean of is the device user using is tablet and in landscape return true
     * rather than that return false
     */
    private boolean isTabletAndLandOrientation() {
        return getResources().getBoolean(R.bool.isTablet) &&
                getResources().getConfiguration().orientation
                        == Configuration.ORIENTATION_LANDSCAPE;
    }

}
